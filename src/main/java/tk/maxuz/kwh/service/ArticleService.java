package tk.maxuz.kwh.service;

import org.springframework.stereotype.Service;
import tk.maxuz.kwh.database.repository.ArticleRepository;
import tk.maxuz.kwh.model.Article;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
