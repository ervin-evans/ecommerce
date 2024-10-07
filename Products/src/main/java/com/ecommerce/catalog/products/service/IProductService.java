package com.ecommerce.catalog.products.service;

import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.requests.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<Product> findProductById(String productId);
    Page<Product> findAllByPage(int page);
    Page<Product> findProductByName(String regex, Pageable pageable);
    Product createNewProduct(ProductRequest productRequest);
    Product updateProduct(String productId, ProductRequest productRequest);
    Product deleteProduct(String productId);

}
