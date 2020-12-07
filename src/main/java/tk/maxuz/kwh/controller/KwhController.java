package tk.maxuz.kwh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import tk.maxuz.kwh.model.Article;
import tk.maxuz.kwh.model.Category;
import tk.maxuz.kwh.service.ArticleService;
import tk.maxuz.kwh.service.CategoryService;

import java.util.List;

@Controller
public class KwhController {

    private final ArticleService articleService;
    private final CategoryService categoryService;

    @Autowired
    public KwhController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @RequestMapping({"/", "/index.html"})
    public String articles(Model model) {
        model.addAttribute("allArticles", this.articleService.findAll());
        return "articles";
    }

    @RequestMapping({"/category/{id}"})
    public String articles(@PathVariable("id") Long categoryId, Model model) {
        model.addAttribute("allArticles", this.articleService.findAll(categoryId));
        return "articles";
    }

    @RequestMapping({"/addArticle"})
    public String addArticle(Model model) {
        model.addAttribute("article", new Article());
        return "articles";
    }

    @RequestMapping({"/editArticle/{id}"})
    public String editArticle(@PathVariable("id") Long id, Model model) {
        model.addAttribute("article", articleService.findById(id));
        return "articles";
    }

    @RequestMapping({"/deleteArticle/{id}"})
    public RedirectView deleteArticle(@PathVariable("id") Long id) {
        articleService.delete(id);
        return new RedirectView("/");
    }

    @RequestMapping({"/saveArticle"})
    public RedirectView saveArticle(@ModelAttribute("article") Article article) {
        articleService.saveArticle(article);
        return new RedirectView("/");
    }

    @ModelAttribute("allCategories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping({"/addCategory"})
    public String addCategory(Model model) {
        model.addAttribute("newCategory", new Category());
        return "articles";
    }

    @RequestMapping({"/saveCategory"})
    public RedirectView saveCategory(@ModelAttribute("newCategory") Category category) {
        categoryService.saveCategory(category);
        return new RedirectView("/");
    }
}
