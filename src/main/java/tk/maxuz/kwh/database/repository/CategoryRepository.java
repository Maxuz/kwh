package tk.maxuz.kwh.database.repository;

import org.springframework.data.repository.CrudRepository;
import tk.maxuz.kwh.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}