package com.example.resumeScreening.Be.service.impl;

import com.example.resumeScreening.Be.entity.Category;
import com.example.resumeScreening.Be.exception.ResourceAlreadyExistException;
import com.example.resumeScreening.Be.exception.ResourceNotFoundException;
import com.example.resumeScreening.Be.repository.CategoryRepository;
import com.example.resumeScreening.Be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;



    @Override
    public String createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new ResourceAlreadyExistException("Category" + category.getName() + " already exists");
        }
        categoryRepository.save(category);
        return "Category saved!";
    }

    @Override
    public String updateCategory(Long categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
        return "Category updated successfully";
    }

    @Override
    public List<Category> categoryList() {
        return categoryRepository.findAll();
    }
}
