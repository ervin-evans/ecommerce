package com.ecommerce.catalog.products.responses;

import com.ecommerce.catalog.products.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Product product;
    private Message message;

}
