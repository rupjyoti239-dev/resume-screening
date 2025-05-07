package com.example.resumeScreening.Be.service;

import com.example.resumeScreening.Be.entity.Category;

import java.util.List;

public interface CategoryService {


    //create
    String createCategory(Category category);


    //update
    String updateCategory(Long categoryId, Category category);


    //list
    List<Category>  categoryList();
}
