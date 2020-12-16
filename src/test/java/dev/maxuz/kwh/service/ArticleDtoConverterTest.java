package dev.maxuz.kwh.service;

import dev.maxuz.kwh.database.repository.CategoryRepository;
import dev.maxuz.kwh.dto.ArticleDto;
import dev.maxuz.kwh.dto.CategoryDto;
import dev.maxuz.kwh.model.Article;
import dev.maxuz.kwh.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ArticleDtoConverterTest {
    private final CategoryDtoConverter categoryDtoConverter = mock(CategoryDtoConverter.class);
    private final MarkdownConverter markdownConverter = mock(MarkdownConverter.class);
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);

    private final ArticleDtoConverter converter =
            new ArticleDtoConverter(categoryDtoConverter, markdownConverter, categoryRepository);

    @Test
    void convertToDto_NullSource_ReturnNull() {
        assertNull(converter.convert((Article) null));
    }

    @Test
    void convertToDto_FullSource_ReturnFull() {
        CategoryDto expectedCategory = new CategoryDto();
        when(categoryDtoConverter.convert(any(Category.class)))
                .thenReturn(expectedCategory);
        when(markdownConverter.convertToHtml(anyString()))
                .thenReturn("Converted to html content");

        Article source = new Article();
        source.setId(123L);
        source.setTitle("Article title");
        source.setCategory(new Category());
        source.setContent("Article content");
        LocalDateTime creationDateTime = LocalDateTime.now().minusDays(1);
        source.setCreationDateTime(creationDateTime);
        LocalDateTime updateDateTime = LocalDateTime.now();
        source.setUpdateDateTime(updateDateTime);

        ArticleDto actual = converter.convert(source);
        assertThat(actual.getId(), is(123L));
        assertThat(actual.getTitle(), is("Article title"));
        assertThat(actual.getCategory(), is(expectedCategory));
        assertThat(actual.getContent(), is("Converted to html content"));
        assertThat(actual.getCreationDateTime(), is(creationDateTime));
        assertThat(actual.getUpdateDateTime(), is(updateDateTime));

        ArgumentCaptor<String> contentArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(markdownConverter).convertToHtml(contentArgumentCaptor.capture());
        assertThat(contentArgumentCaptor.getValue(), is("Article content"));
    }

    @Test
    void convertFromDto_NullSource_ReturnNull() {
        assertNull(converter.convert((ArticleDto) null));
    }

    @Test
    void convertFromDto_FullSource_ReturnFull() {
        Category expectedCategory = new Category();
        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(expectedCategory));

        ArticleDto source = new ArticleDto();
        source.setId(123L);
        source.setTitle("Article title");
        source.setCategory(new CategoryDto(321L, "Category name"));
        source.setContent("Article content");
        LocalDateTime creationDateTime = LocalDateTime.now().minusDays(1);
        source.setCreationDateTime(creationDateTime);
        LocalDateTime updateDateTime = LocalDateTime.now();
        source.setUpdateDateTime(updateDateTime);

        Article actual = converter.convert(source);
        assertThat(actual.getId(), is(123L));
        assertThat(actual.getTitle(), is("Article title"));
        assertThat(actual.getCategory(), is(expectedCategory));
        assertThat(actual.getContent(), is("Article content"));
        assertThat(actual.getCreationDateTime(), is(creationDateTime));
        assertThat(actual.getUpdateDateTime(), is(updateDateTime));

        ArgumentCaptor<Long> categoryIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(categoryRepository).findById(categoryIdArgumentCaptor.capture());
        assertThat(categoryIdArgumentCaptor.getValue(), is(321L));
    }
}