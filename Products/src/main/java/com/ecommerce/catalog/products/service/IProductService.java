package com.ecommerce.catalog.products.service;

import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.requests.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface IProductService {
    Product createNewProduct(ProductRequest productRequest);
    Product updateProduct(String productId, ProductRequest productRequest);

    Optional<Product> findProductById(String productId);
}
