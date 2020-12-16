package dev.maxuz.kwh.database.repository;

import org.springframework.data.repository.CrudRepository;
import dev.maxuz.kwh.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}