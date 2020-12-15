package tk.maxuz.kwh.service;

import org.springframework.stereotype.Service;
import tk.maxuz.kwh.database.repository.CategoryRepository;
import tk.maxuz.kwh.dto.ArticleDto;
import tk.maxuz.kwh.model.Article;

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
        article.setContent(markdownConverter.convertToHtml(source.getContent()));
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
