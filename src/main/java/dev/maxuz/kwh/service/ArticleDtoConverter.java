package dev.maxuz.kwh.service;

import dev.maxuz.kwh.database.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import dev.maxuz.kwh.dto.ArticleDto;
import dev.maxuz.kwh.model.Article;

@Service
public class ArticleDtoConverter {
    private final CategoryDtoConverter categoryDtoConverter;
    private final MarkdownConverter markdownConverter;
    private final CategoryRepository categoryRepository;

    public ArticleDtoConverter(CategoryDtoConverter categoryDtoConverter, MarkdownConverter markdownConverter,
                               CategoryRepository categoryRepository) {
        this.categoryDtoConverter = categoryDtoConverter;
        this.markdownConverter = markdownConverter;
        this.categoryRepository = categoryRepository;
    }

    public ArticleDto convert(Article source) {
        if (source == null) {
            return null;
        }
        ArticleDto article = new ArticleDto();
        article.setId(source.getId());
        article.setTitle(source.getTitle());
        article.setCategory(categoryDtoConverter.convert(source.getCategory()));
        article.setContent(source.getContent());
        article.setHtmlContent(markdownConverter.convertToHtml(source.getContent()));
        article.setCreationDateTime(source.getCreationDateTime());
        article.setUpdateDateTime(source.getUpdateDateTime());

        return article;
    }

    public Article convert(ArticleDto source) {
        if (source == null) {
            return null;
        }
        Article article = new Article();
        article.setId(source.getId());
        article.setTitle(source.getTitle());
        article.setCategory(categoryRepository.findById(source.getCategory().getId()).orElseThrow());
        article.setContent(source.getContent());
        article.setCreationDateTime(source.getCreationDateTime());
        article.setUpdateDateTime(source.getUpdateDateTime());

        return article;
    }
}
