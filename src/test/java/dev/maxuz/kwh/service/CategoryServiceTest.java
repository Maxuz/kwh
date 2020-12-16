package dev.maxuz.kwh.service;

import dev.maxuz.kwh.database.repository.CategoryRepository;
import dev.maxuz.kwh.dto.CategoryDto;
import dev.maxuz.kwh.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryDtoConverter categoryDtoConverter = mock(CategoryDtoConverter.class);
    private final CategoryService categoryService = new CategoryService(categoryRepository, categoryDtoConverter);

    private Category fakeCategory() {
        return new Category();
    }

    @Test
    void getAllCategories() {
        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(fakeCategory(), fakeCategory()));
        when(categoryDtoConverter.convert(any(Category.class)))
                .thenReturn(new CategoryDto());
        assertThat(categoryService.getAllCategories(), is(Arrays.asList(new CategoryDto(), new CategoryDto())));
    }

    @Test
    void saveCategory() {
        Category category = new Category();
        category.setName("New category name");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("New category name");

        when(categoryRepository.save(any())).thenReturn(category);
        when(categoryDtoConverter.convert(any(CategoryDto.class))).thenReturn(category);

        categoryService.saveCategory(categoryDto);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        ArgumentCaptor<CategoryDto> categoryDtoArgumentCaptor = ArgumentCaptor.forClass(CategoryDto.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        verify(categoryDtoConverter).convert(categoryDtoArgumentCaptor.capture());

        assertThat(categoryArgumentCaptor.getValue(), is(category));
        assertThat(categoryDtoArgumentCaptor.getValue(), is(categoryDto));
    }
}