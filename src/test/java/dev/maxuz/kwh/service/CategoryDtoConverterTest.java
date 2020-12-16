package dev.maxuz.kwh.service;

import dev.maxuz.kwh.dto.CategoryDto;
import dev.maxuz.kwh.model.Category;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class CategoryDtoConverterTest {
    private final CategoryDtoConverter converter = new CategoryDtoConverter();

    @Test
    void convertToDto_NullSource_ReturnNull() {
        assertNull(converter.convert((Category) null));
    }

    @Test
    void convertToDto_FullDataSource_ReturnFull() {
        Category source = new Category();
        source.setId(111L);
        source.setName("Category name");

        CategoryDto actual = converter.convert(source);
        assertThat(actual.getId(), is(111L));
        assertThat(actual.getName(), is("Category name"));
    }

    @Test
    void convertFromDto_NullSource_ReturnNull() {
        assertNull(converter.convert((CategoryDto) null));
    }

    @Test
    void convertFromDto_FullDataSource_ReturnFull() {
        CategoryDto source = new CategoryDto();
        source.setId(111L);
        source.setName("Category name");

        Category actual = converter.convert(source);
        assertThat(actual.getId(), is(111L));
        assertThat(actual.getName(), is("Category name"));
    }
}