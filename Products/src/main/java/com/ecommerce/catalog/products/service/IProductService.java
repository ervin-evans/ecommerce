package com.ecommerce.catalog.products.service;

import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.requests.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {
    public Product createNewProduct(ProductRequest productRequest);
}
