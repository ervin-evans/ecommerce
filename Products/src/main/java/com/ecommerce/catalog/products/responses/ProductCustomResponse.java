package com.ecommerce.catalog.products.responses;

import com.ecommerce.catalog.products.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCustomResponse {
    private List<Product> products;
    private int totalPages;
    private long totalElements;
    private int elementsPerPage;
    private boolean empty;
    private boolean first;
    private boolean last;
}
