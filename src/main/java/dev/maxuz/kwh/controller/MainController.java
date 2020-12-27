package dev.maxuz.kwh.controller;

import dev.maxuz.kwh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import dev.maxuz.kwh.dto.ArticleDto;
import dev.maxuz.kwh.dto.CategoryDto;
import dev.maxuz.kwh.service.ArticleService;

import java.util.List;

@Controller
public class MainController {
    private final ArticleService articleService;
    private final CategoryService categoryService;

    @ModelAttribute("allCategories")
    public List<CategoryDto> getCategories() {
        return categoryService.getAllCategories();
    }

    @Autowired
    public MainController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
    public String articles(Model model) {
        model.addAttribute("allArticles", this.articleService.findAll());
        return "articles";
    }

    @RequestMapping(value = {"/category/{id}"}, method = RequestMethod.GET)
    public String articles(@PathVariable("id") Long categoryId, Model model) {
        model.addAttribute("allArticles", this.articleService.findAllByCategoryId(categoryId));
        return "articles";
    }

    @RequestMapping(value = {"/article/new"}, method = RequestMethod.GET)
    public String addArticle(Model model) {
        model.addAttribute("article", new ArticleDto());
        return "edit-article";
    }

    @RequestMapping(value = {"/article/{id}"}, method = RequestMethod.GET)
    public String editArticle(@PathVariable("id") Long id, Model model) {
        model.addAttribute("article", articleService.findById(id));
        return "edit-article";
    }

    @RequestMapping(value = {"/deleteArticle/{id}"}, method = RequestMethod.POST)
    public RedirectView deleteArticle(@PathVariable("id") Long id) {
        articleService.delete(id);
        return new RedirectView("/");
    }

    @RequestMapping(value = {"/article"} , method = RequestMethod.POST)
    public RedirectView saveArticle(@ModelAttribute("article") ArticleDto article) {
        if (article.getId() == null) {
            articleService.saveNewArticle(article);
        } else {
            articleService.editArticle(article);
        }
        return new RedirectView("/");
    }

    @RequestMapping(value = {"/category/new"}, method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("newCategory", new CategoryDto());
        return "add-category";
    }

    @RequestMapping(value = {"/category"}, method = RequestMethod.POST)
    public RedirectView saveCategory(@ModelAttribute("newCategory") CategoryDto category) {
        categoryService.saveCategory(category);
        return new RedirectView("/");
    }
}
