package tk.maxuz.kwh.database.repository;

import org.springframework.data.repository.CrudRepository;
import tk.maxuz.kwh.model.Article;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAllByDeletedIsFalseOrderByCreationDateTimeDesc();
}