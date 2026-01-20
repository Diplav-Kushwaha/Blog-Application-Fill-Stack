package com.diplav.blog.service;

import com.diplav.blog.entity.Category;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface CategoryService {
    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategory(Long id);
    @Nullable Object getAllCategories();
}
