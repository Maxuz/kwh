package tk.maxuz.kwh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.maxuz.kwh.model.Article;
import tk.maxuz.kwh.service.ArticleService;

import java.util.List;

@Controller
public class KwhController {

    private final ArticleService articleService;

    @Autowired
    public KwhController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute("allArticles")
    public List<Article> populateSeedStarters() {
        return this.articleService.findAll();
    }

    @RequestMapping({"/", "/index.html"})
    public String articles() {
        return "articles";
    }


}
