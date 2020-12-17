package dev.maxuz.kwh.service;

import dev.maxuz.kwh.database.repository.ArticleRepository;
import dev.maxuz.kwh.database.repository.CategoryRepository;
import dev.maxuz.kwh.dto.ArticleDto;
import dev.maxuz.kwh.dto.CategoryDto;
import dev.maxuz.kwh.model.Article;
import dev.maxuz.kwh.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArticleServiceTest {
    private final ArticleRepository articleRepository = mock(ArticleRepository.class);
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final ArticleDtoConverter articleDtoConverter = mock(ArticleDtoConverter.class);

    private final ArticleService articleService = new ArticleService(articleRepository, categoryRepository, articleDtoConverter);

    @Test
    void findAll() {
        when(articleRepository.findAllByDeletedIsFalseOrderByCreationDateTimeDesc())
                .thenReturn(Arrays.asList(new Article(), new Article()));
        when(articleDtoConverter.convert(any(Article.class)))
                .thenReturn(new ArticleDto());

        assertThat(articleService.findAll(), is(Arrays.asList(new ArticleDto(), new ArticleDto())));
    }

    @Test
    void findAllByCategoryId() {
        when(articleRepository.findAllByCategoryIdAndDeletedIsFalseOrderByCreationDateTimeDesc(any()))
                .thenReturn(Arrays.asList(new Article(), new Article()));
        when(articleDtoConverter.convert(any(Article.class)))
                .thenReturn(new ArticleDto());

        assertThat(articleService.findAllByCategoryId(123L), is(Arrays.asList(new ArticleDto(), new ArticleDto())));

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(articleRepository).findAllByCategoryIdAndDeletedIsFalseOrderByCreationDateTimeDesc(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue(), is(123L));
    }

    @Test
    void findById_ArticleExists() {
        when(articleRepository.findById(any())).thenReturn(Optional.of(new Article()));
        ArticleDto expected = new ArticleDto();
        when(articleDtoConverter.convert(any(Article.class)))
                .thenReturn(expected);

        assertThat(articleService.findById(123L), is(expected));
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(articleRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue(), is(123L));
    }

    @Test
    void findById_ArticleDoesNotExist() {
        when(articleRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> articleService.findById(123L));
    }

    @Test
    void saveNewArticle() {
        Category expectedCategory = new Category();
        ArticleDto source = new ArticleDto();
        Article convertedArticle = new Article();
        convertedArticle.setTitle("Article title");
        convertedArticle.setCategory(expectedCategory);
        convertedArticle.setContent("Article content");

        when(categoryRepository.findById(eq(321L))).thenReturn(Optional.of(expectedCategory));
        when(articleDtoConverter.convert(eq(source))).thenReturn(convertedArticle);
        when(articleRepository.save(any())).thenReturn(new Article());

        articleService.saveNewArticle(source);
        ArgumentCaptor<Article> articleArgumentCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleRepository).save(articleArgumentCaptor.capture());

        assertThat(articleArgumentCaptor.getValue().getId(), is(nullValue()));
        assertThat(articleArgumentCaptor.getValue().getTitle(), is("Article title"));
        assertThat(articleArgumentCaptor.getValue().getContent(), is("Article content"));
        assertThat(articleArgumentCaptor.getValue().getCategory(), is(expectedCategory));
        assertThat(articleArgumentCaptor.getValue().getCreationDateTime().withNano(0), is(LocalDateTime.now().withNano(0)));
        assertThat(articleArgumentCaptor.getValue().getUpdateDateTime(), is(nullValue()));
        assertThat(articleArgumentCaptor.getValue().getDeleted(), is(false));
    }

    @Test
    void editArticle() {
        LocalDateTime creationDateTime = LocalDateTime.now().minusDays(1);
        Article sourceArticle = new Article();
        sourceArticle.setId(123L);
        sourceArticle.setCreationDateTime(creationDateTime);
        sourceArticle.setDeleted(false);

        Category changedCategory = new Category();

        ArticleDto source = new ArticleDto();
        source.setId(123L);
        source.setTitle("Edited title");
        source.setContent("Edited content");
        source.setCategory(new CategoryDto(1234L, "Edited category"));

        when(articleRepository.findById(any())).thenReturn(Optional.of(sourceArticle));
        when(articleRepository.save(any())).thenReturn(new Article());
        when(categoryRepository.findById(eq(1234L))).thenReturn(Optional.of(changedCategory));

        articleService.editArticle(source);

        ArgumentCaptor<Article> articleArgumentCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleRepository).save(articleArgumentCaptor.capture());

        assertThat(articleArgumentCaptor.getValue().getId(), is(123L));
        assertThat(articleArgumentCaptor.getValue().getTitle(), is("Edited title"));
        assertThat(articleArgumentCaptor.getValue().getContent(), is("Edited content"));
        assertThat(articleArgumentCaptor.getValue().getCategory(), is(changedCategory));
        assertThat(articleArgumentCaptor.getValue().getCreationDateTime(), is(creationDateTime));
        assertThat(articleArgumentCaptor.getValue().getUpdateDateTime().withNano(0), is(LocalDateTime.now().withNano(0)));
        assertThat(articleArgumentCaptor.getValue().getDeleted(), is(false));
    }

    @Test
    void deleteArticle() {
        Article source = new Article();
        source.setId(123L);
        source.setDeleted(false);

        when(articleRepository.findById(any())).thenReturn(Optional.of(source));
        when(articleRepository.save(any())).thenReturn(new Article());

        articleService.delete(123L);

        ArgumentCaptor<Article> articleArgumentCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleRepository).save(articleArgumentCaptor.capture());

        assertThat(articleArgumentCaptor.getValue().getDeleted(), is(true));

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(articleRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue(), is(123L));
    }
}