package dev.maxuz.kwh;

import dev.maxuz.kwh.controller.MainController;
import dev.maxuz.kwh.database.service.DaoUserDetailsService;
import dev.maxuz.kwh.dto.ArticleDto;
import dev.maxuz.kwh.dto.CategoryDto;
import dev.maxuz.kwh.service.ArticleService;
import dev.maxuz.kwh.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;
    @MockBean
    private CategoryService categoryService;

    @SuppressWarnings("unused")
    @MockBean
    private DaoUserDetailsService daoUserDetailsService;

    private ArticleDto fakeArticle() {
        ArticleDto article = new ArticleDto();
        article.setId(1L);
        article.setTitle("Article title");
        article.setContent("Article content");
        article.setCategory(fakeCategory());
        return article;
    }

    private CategoryDto fakeCategory() {
        CategoryDto category = new CategoryDto();
        category.setId(2L);
        category.setName("Category name");
        return category;
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/index.html", "/addArticle", "/editArticle/1", "/allCategories", "/addCategory"})
    public void checkGetRequestsAccessWithoutLogin_RedirectionToLoginExpected(String url) throws Exception {
        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "http://localhost/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/index.html", "/deleteArticle/1", "/saveArticle", "/saveCategory"})
    public void checkPostRequestsAccessWithoutLogin_RedirectionToLoginExpected(String url) throws Exception {
        this.mockMvc.perform(post(url))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @WithMockUser
    @ParameterizedTest
    @ValueSource(strings = {"/", "/index.html"})
    public void getMainPageWithArticles_ArticlesAndCategoriesExpected(String url) throws Exception {
        List<ArticleDto> articles = Arrays.asList(fakeArticle(), fakeArticle());
        when(articleService.findAll())
                .thenReturn(articles);
        List<CategoryDto> categories = Arrays.asList(fakeCategory(), fakeCategory());
        when(categoryService.getAllCategories())
                .thenReturn(categories);
        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("allArticles", is(articles)))
                .andExpect(model().attribute("allCategories", is(categories)));
    }

    @WithMockUser
    @Test
    public void getArticlesByCategory_ArticlesByCategoryExpected() throws Exception {
        List<ArticleDto> articles = Collections.singletonList(fakeArticle());
        when(articleService.findAllByCategoryId(anyLong()))
                .thenReturn(articles);
        this.mockMvc.perform(get("/category/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("allArticles", is(articles)));

        ArgumentCaptor<Long> categoryIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(articleService).findAllByCategoryId(categoryIdArgumentCaptor.capture());
        assertThat(categoryIdArgumentCaptor.getValue(), is(123L));
    }

    @WithMockUser
    @Test
    public void addArticle_AddArticleExpected() throws Exception {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setCategory(new CategoryDto());
        this.mockMvc.perform(get("/addArticle"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("article", is(articleDto)));
    }

    @WithMockUser
    @Test
    public void editArticle_ArticleExpected() throws Exception {
        ArticleDto article = fakeArticle();
        when(articleService.findById(anyLong()))
                .thenReturn(article);
        this.mockMvc.perform(get("/editArticle/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("article", is(article)));
        ArgumentCaptor<Long> articleIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(articleService).findById(articleIdArgumentCaptor.capture());
        assertThat(articleIdArgumentCaptor.getValue(), is(123L));
    }

    @WithMockUser
    @Test
    public void deleteArticle_DeleteArticleCallExpected() throws Exception {
        this.mockMvc.perform(post("/deleteArticle/123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Long> articleIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(articleService).delete(articleIdArgumentCaptor.capture());
        assertThat(articleIdArgumentCaptor.getValue(), is(123L));
    }

    @WithMockUser
    @Test
    public void saveNewArticle_DataFromRequestExpected() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("title", "New title");
        params.set("content", "New article content");
        params.set("category.id", "123");

        this.mockMvc.perform(post("/saveArticle").params(params))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<ArticleDto> articleArgumentCaptor = ArgumentCaptor.forClass(ArticleDto.class);
        verify(articleService).saveNewArticle(articleArgumentCaptor.capture());

        assertThat(articleArgumentCaptor.getValue().getTitle(), is("New title"));
        assertThat(articleArgumentCaptor.getValue().getContent(), is("New article content"));
        assertThat(articleArgumentCaptor.getValue().getCategory().getId(), is(123L));
    }

    @WithMockUser
    @Test
    public void saveExistArticle_DataFromRequestExpected() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("id", "123");
        params.set("title", "Exist article title");
        params.set("content", "Exist article content");
        params.set("category.id", "321");

        this.mockMvc.perform(post("/saveArticle").params(params))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<ArticleDto> articleArgumentCaptor = ArgumentCaptor.forClass(ArticleDto.class);
        verify(articleService).editArticle(articleArgumentCaptor.capture());

        assertThat(articleArgumentCaptor.getValue().getId(), is(123L));
        assertThat(articleArgumentCaptor.getValue().getTitle(), is("Exist article title"));
        assertThat(articleArgumentCaptor.getValue().getContent(), is("Exist article content"));
        assertThat(articleArgumentCaptor.getValue().getCategory().getId(), is(321L));
    }

    @WithMockUser
    @Test
    public void addCategory_AddCategoryExpected() throws Exception {
        this.mockMvc.perform(get("/addCategory"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("newCategory", is(new CategoryDto())));
    }

    @WithMockUser
    @Test
    public void saveNewCategory_DataFromRequestExpected() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("name", "New category name");

        this.mockMvc.perform(post("/saveCategory").params(params))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<CategoryDto> categoryArgumentCaptor = ArgumentCaptor.forClass(CategoryDto.class);
        verify(categoryService).saveCategory(categoryArgumentCaptor.capture());

        assertThat(categoryArgumentCaptor.getValue().getName(), is("New category name"));
    }
}