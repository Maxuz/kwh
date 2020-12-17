package dev.maxuz.kwh.service;

import dev.maxuz.kwh.database.repository.ArticleRepository;
import dev.maxuz.kwh.database.repository.CategoryRepository;
import dev.maxuz.kwh.dto.ArticleDto;
import dev.maxuz.kwh.model.Article;
import dev.maxuz.kwh.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public void saveNewArticle(ArticleDto articleDto) {
        Article article = articleDtoConverter.convert(articleDto);
        article.setCreationDateTime(LocalDateTime.now());
        article.setDeleted(false);
        articleRepository.save(article);
    }

    public void editArticle(ArticleDto articleDto) {
        Article article = articleRepository.findById(articleDto.getId()).orElseThrow();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setUpdateDateTime(LocalDateTime.now());
        Category category = categoryRepository.findById(articleDto.getCategory().getId()).orElseThrow();
        article.setCategory(category);
        articleRepository.save(article);
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
