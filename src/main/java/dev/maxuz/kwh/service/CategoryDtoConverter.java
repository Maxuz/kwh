package dev.maxuz.kwh.service;

import org.springframework.stereotype.Service;
import dev.maxuz.kwh.dto.CategoryDto;
import dev.maxuz.kwh.model.Category;

@Service
public class CategoryDtoConverter {

    public CategoryDto convert(Category source) {
        if (source == null) {
            return null;
        }
        CategoryDto category = new CategoryDto();
        category.setId(source.getId());
        category.setName(source.getName());
        return category;
    }

    public Category convert(CategoryDto source) {
        if (source == null) {
            return null;
        }
        Category category = new Category();
        category.setId(source.getId());
        category.setName(source.getName());
        return category;
    }
}
