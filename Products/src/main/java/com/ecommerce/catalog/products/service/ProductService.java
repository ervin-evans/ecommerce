package com.ecommerce.catalog.products.service;

import com.ecommerce.catalog.products.exceptions.ProductNotFoundException;
import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.repository.ProductRepository;
import com.ecommerce.catalog.products.requests.ProductRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createNewProduct(ProductRequest productRequest) {
        Product newProduct = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(String productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFoundException(productId));
        product = Product.builder()
                .id(productId)
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();
        return productRepository.save(product);
    }


    @Override
    public Optional<Product> findProductById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Page<Product> findAllByPage(int page) {
        if(page<0)page=0;
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.findAll(pageable);
    }


}
