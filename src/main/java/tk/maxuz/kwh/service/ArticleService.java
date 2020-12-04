package tk.maxuz.kwh.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.maxuz.kwh.database.repository.ArticleRepository;
import tk.maxuz.kwh.model.Article;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAllByOrderByDateTimeDesc();
    }

    public void saveArticle(Article article) {
        article.setDateTime(LocalDateTime.now());
        articleRepository.save(article);
    }
}
