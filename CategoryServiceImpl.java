package com.diplav.blog.serviceImpl;

import com.diplav.blog.entity.Category;
import com.diplav.blog.repository.CategoryRepository;
import com.diplav.blog.service.CategoryService;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category already exists!");
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Category not found"));

        if (!category.getPosts().isEmpty()) {
            throw new IllegalStateException("Category has posts associated");
        }
        categoryRepository.delete(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
