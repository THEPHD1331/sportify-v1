package com.sportify.app.controller;

import com.sportify.app.dto.response.ProductDTO;
import com.sportify.app.dto.request.ProductRequest;
import com.sportify.app.dto.response.ApiResponse;
import com.sportify.app.entity.Product;
import com.sportify.app.exception.AlreadyExistsException;
import com.sportify.app.exception.ProductNotFoundException;
import com.sportify.app.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Author: Paras Dongre
 * Date Created:27-01-2025
 * Time Created:19:26
 */
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(){

        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse> getProductsByName(@RequestParam String productName){

        List<ProductDTO> products = productService.getProductByName(productName);
        if (products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", null));

        return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String categoryName){

        List<ProductDTO> products = productService.getProductsByCategory(categoryName);
        if (products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", null));

        return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brandName){

        List<ProductDTO> products = productService.getProductByBrand(brandName);
        if (products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", null));

        return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    @GetMapping("/by-category-and-brand")
    public ResponseEntity<ApiResponse> getByProductsCategoryAndBrand(@RequestParam String categoryName,
                                                                     @RequestParam String brandName){

        List<ProductDTO> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
        if (products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not Found", null));

        return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable long id){

        try{
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Success", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductRequest productRequest){

        try{
            Product product = productService.addProduct(productRequest);
            return ResponseEntity.ok(new ApiResponse("Success", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable long id,
                                                     @RequestBody ProductRequest productRequest){
        try{
            Product product = productService.updateProduct(productRequest, id);
            return ResponseEntity.ok(new ApiResponse("Success", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable long id){
        try{
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Product Deleted", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
