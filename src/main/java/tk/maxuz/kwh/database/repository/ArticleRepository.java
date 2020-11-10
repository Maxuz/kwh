package tk.maxuz.kwh.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.maxuz.kwh.model.Article;
import tk.maxuz.kwh.model.User;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}