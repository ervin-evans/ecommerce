package com.ecommerce.catalog.products.service;

import com.ecommerce.catalog.products.exceptions.ProductNotFoundException;
import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.repository.ProductRepository;
import com.ecommerce.catalog.products.requests.ProductRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Value("${ecommerce.products.pages.size:10}")
    private Integer pageSize;


    /******************************************************************************************************************
     *                                          CREATE NEW PRODUCT
     ******************************************************************************************************************/
    @Override
    public Product createNewProduct(ProductRequest productRequest) {
        Product newProduct = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        return productRepository.save(newProduct);
    }

    /******************************************************************************************************************
     *                                                  UPDATE PRODUCT
     ******************************************************************************************************************/
    @Override
    public Product updateProduct(String productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        product = Product.builder()
                .id(productId)
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();
        return productRepository.save(product);
    }

    /******************************************************************************************************************
     *                                              DELETE PRODUCT
     ******************************************************************************************************************/
    @Override
    public Product deleteProduct(String productId) {
        Product productDatabase = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        productRepository.deleteById(productId);
        return productDatabase;
    }

    /******************************************************************************************************************
     *                                              FIND PRODUCT BY ID
     ******************************************************************************************************************/
    @Override
    public Optional<Product> findProductById(String productId) {
        return productRepository.findById(productId);
    }

    /******************************************************************************************************************
     *                                         FIND ALL PRODUCTS BY PAGE
     ******************************************************************************************************************/
    @Override
    public Page<Product> findAllByPage(int page) {
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findProductByName(String regex, Pageable pageable) {
        logger.info("Iniciando busqueda de productos filtrados por nombre: {}", regex);
        Page<Product> byNameRegex = productRepository.findByNameRegex(regex, pageable);
        logger.info("Busqueda completada, se encontraron {} productos", byNameRegex.getTotalElements());
        return byNameRegex;
    }


}
