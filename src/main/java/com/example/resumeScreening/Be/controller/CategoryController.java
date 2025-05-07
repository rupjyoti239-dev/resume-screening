package com.example.resumeScreening.Be.controller;


import com.example.resumeScreening.Be.entity.Category;
import com.example.resumeScreening.Be.response.ApiResponse;
import com.example.resumeScreening.Be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;



    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> createCategory(@RequestBody Category category) {
        String data = categoryService.createCategory(category);
        ApiResponse<?> response = new ApiResponse<>(
                true,
                data,
                LocalDateTime.now()
        );
       return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        String data = categoryService.updateCategory(categoryId,category);
        ApiResponse<?> response = new ApiResponse<>(
                true,
                data,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_HR')")
    public ResponseEntity<ApiResponse<?>> getAllCategories() {
        List<Category> categories = categoryService.categoryList();
        ApiResponse<?> response = new ApiResponse<>(
                true,
                categories,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
