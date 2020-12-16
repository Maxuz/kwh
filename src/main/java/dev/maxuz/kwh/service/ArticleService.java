package dev.maxuz.kwh.service;

import dev.maxuz.kwh.database.repository.CategoryRepository;
import dev.maxuz.kwh.model.Article;
import dev.maxuz.kwh.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import dev.maxuz.kwh.database.repository.ArticleRepository;
import dev.maxuz.kwh.dto.ArticleDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleDtoConverter articleDtoConverter;

    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository,
                          ArticleDtoConverter articleDtoConverter) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.articleDtoConverter = articleDtoConverter;
    }

    public List<ArticleDto> findAll() {
        return articleRepository.findAllByDeletedIsFalseOrderByCreationDateTimeDesc().stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public ArticleDto findById(Long id) {
        return articleRepository.findById(id)
                .map(articleDtoConverter::convert)
                .orElseThrow();
    }

    public void saveArticle(ArticleDto articleDto) {
        Optional<Article> optionalArticle = Optional.ofNullable(articleDto.getId()).flatMap(articleRepository::findById);
        if (optionalArticle.isPresent()) {
            Article editedArticle = optionalArticle.get();
            editedArticle.setTitle(articleDto.getTitle());
            editedArticle.setContent(articleDto.getContent());
            editedArticle.setUpdateDateTime(LocalDateTime.now());
            Category category = categoryRepository.findById(articleDto.getCategory().getId()).orElseThrow();
            editedArticle.setCategory(category);
            articleRepository.save(editedArticle);
        } else {
            Article article = articleDtoConverter.convert(articleDto);
            article.setCreationDateTime(LocalDateTime.now());
            article.setDeleted(false);
            articleRepository.save(article);
        }
    }

    public void delete(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        article.setDeleted(true);
        articleRepository.save(article);
    }

    public List<ArticleDto> findAllByCategoryId(Long categoryId) {
        return articleRepository.findAllByCategoryIdAndDeletedIsFalseOrderByCreationDateTimeDesc(categoryId).stream()
                .map(articleDtoConverter::convert)
                .collect(Collectors.toList());
    }
}
