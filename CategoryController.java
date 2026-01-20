package com.diplav.blog.controller;

import com.diplav.blog.entity.Category;
import com.diplav.blog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/allCategories")
    public String getAllCategory(Model model){
        model.addAttribute("categories", categoryService.listCategories());
        model.addAttribute("category", new Category());
        return "home-categories";
    }

    @GetMapping("/newCategory")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "new-category-form";
    }

    @PostMapping("/createCategory")
    public String createCategory(@ModelAttribute Category category){
        categoryService.createCategory(category);
        return "redirect:/api/v1/categories/allCategories";
    }

    @RequestMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return "redirect:/api/v1/categories/allCategories";
    }
}
