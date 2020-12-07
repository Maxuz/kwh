package tk.maxuz.kwh.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.maxuz.kwh.database.repository.ArticleRepository;
import tk.maxuz.kwh.model.Article;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAllByDeletedIsFalseOrderByCreationDateTimeDesc();
    }

    public Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow();
    }

    public void saveArticle(Article article) {
        if (article.getId() == null) {
            article.setCreationDateTime(LocalDateTime.now());
            article.setDeleted(false);
            articleRepository.save(article);
        } else {
            Article editedArticle = articleRepository.findById(article.getId()).orElseThrow();
            editedArticle.setTitle(article.getTitle());
            editedArticle.setContent(article.getContent());
            editedArticle.setUpdateDateTime(LocalDateTime.now());
            articleRepository.save(editedArticle);
        }
    }

    public void delete(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        article.setDeleted(true);
        articleRepository.save(article);
    }

    public List<Article> findAll(Long categoryId) {
        return articleRepository.findAllByCategoryIdAndDeletedIsFalseOrderByCreationDateTimeDesc(categoryId);
    }
}
