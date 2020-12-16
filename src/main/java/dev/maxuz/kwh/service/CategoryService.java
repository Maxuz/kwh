package dev.maxuz.kwh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.maxuz.kwh.database.repository.CategoryRepository;
import dev.maxuz.kwh.dto.CategoryDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryDtoConverter categoryDtoConverter;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryDtoConverter categoryDtoConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryDtoConverter = categoryDtoConverter;
    }

    public List<CategoryDto> getAllCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(categoryDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public void saveCategory(CategoryDto category) {
        categoryRepository.save(categoryDtoConverter.convert(category));
    }
}
