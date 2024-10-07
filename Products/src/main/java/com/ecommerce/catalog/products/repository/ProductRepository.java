package com.ecommerce.catalog.products.repository;

import com.ecommerce.catalog.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{ $text: { $search: ?0 } }")
    Page<Product> findByNameRegex(String regex, Pageable pageable);
}
