package dev.maxuz.kwh.database.repository;

import dev.maxuz.kwh.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAllByDeletedIsFalseOrderByCreationDateTimeDesc();

    List<Article> findAllByCategoryIdAndDeletedIsFalseOrderByCreationDateTimeDesc(Long categoryId);
}