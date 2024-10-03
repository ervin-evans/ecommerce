package com.ecommerce.catalog.products.service;

import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.requests.ProductRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IProductService {
    Optional<Product> findProductById(String productId);
    Page<Product> findAllByPage(int page);
    Product createNewProduct(ProductRequest productRequest);
    Product updateProduct(String productId, ProductRequest productRequest);
    Product deleteProduct(String productId);

}
