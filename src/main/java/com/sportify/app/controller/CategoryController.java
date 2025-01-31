package com.sportify.app.controller;

import com.sportify.app.dto.response.ApiResponse;
import com.sportify.app.entity.Category;
import com.sportify.app.exception.AlreadyExistsException;
import com.sportify.app.exception.ResourceNotFoundException;
import com.sportify.app.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Paras Dongre
 * Date Created:27-01-2025
 * Time Created:19:27
 */
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(){

        try{
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Success", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){

        try {
            Category category1 = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Category Added", category1));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable long id){

        try {
            Category category1 = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", category1));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){

        try {
            Category category1 = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", category1));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable long id, @RequestBody Category category){

        try {
            Category category1 = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Updated", category1));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable long id){

        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Category Deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }
}
