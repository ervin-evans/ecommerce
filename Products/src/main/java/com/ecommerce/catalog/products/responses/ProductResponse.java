package com.ecommerce.catalog.products.responses;

import com.ecommerce.catalog.products.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Map<String, Object>product;

}
