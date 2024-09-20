package com.ecommerce.catalog.products.controller;

import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.requests.ProductRequest;
import com.ecommerce.catalog.products.responses.ProductResponse;
import com.ecommerce.catalog.products.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public String sayHello() {
        return "hello";
    }

    @Autowired
    private IProductService iProductService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest, BindingResult result) {
        ProductResponse productResponse = null;
        if (result.hasErrors()) {
            Map<String, Object> validationErrors = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            validationErrors.put("type", "validation");
            productResponse = ProductResponse.builder()
                    .product(Map.of("errors", validationErrors))
                    .build();
            return ResponseEntity.badRequest().body(productResponse);
        }
        try {
            Product savedProduct = iProductService.createNewProduct(productRequest);
            productResponse = ProductResponse.builder()
                    .product(Map.of("product", savedProduct, "message", "El producto fue guardado exitosamente"))
                    .build();
            return ResponseEntity.ok(productResponse);
        } catch (DataAccessException e) {
            productResponse = ProductResponse.builder()
                    .product(Map.of("errors", e.getMessage()))
                    .build();
            return ResponseEntity.internalServerError().body(productResponse);
        }
    }

}
