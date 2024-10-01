package com.ecommerce.catalog.products.controller;

import com.ecommerce.catalog.products.exceptions.ProductNotFoundException;
import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.requests.ProductRequest;
import com.ecommerce.catalog.products.responses.*;
import com.ecommerce.catalog.products.service.IProductService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LogManager.getLogger();

    @GetMapping
    public String sayHello() {
        return "hello";
    }

    @Autowired
    private IProductService iProductService;

    /******************************************************************************************************************
     * CREATE NEW PRODUCT
     ******************************************************************************************************************/

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest) {
            Product savedProduct = iProductService.createNewProduct(productRequest);
            Message message = Message.builder()
                    .message("EL producto ha sido guardado")
                    .type(MessageType.INFO)
                    .build();
            ProductResponse productResponse = ProductResponse.builder()
                    .product(savedProduct)
                    .message(message)
                    .build();
            return ResponseEntity.ok(productResponse);

    }

    /******************************************************************************************************************
     *                                                  UPDATE PRODUCT
     ******************************************************************************************************************/

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("productId") String productId,
            @Valid @RequestBody ProductRequest productRequest) {

        //Verificamos que el producto exista
        Product existingProduct = iProductService.findProductById(productId)
                .orElseThrow(()->new ProductNotFoundException(productId));

        //Actualizamos el producto
        Product updatedProduct =  iProductService.updateProduct(productId, productRequest);

        Message message = Message.builder()
                .message("El producto " + updatedProduct.getName() + " ha sido actualizado!")
                .type(MessageType.INFO)
                .build();
        ProductResponse response = ProductResponse.builder()
                .product(updatedProduct)
                .message(message)
                .build();

            return ResponseEntity.ok().body(response);

    }

}
