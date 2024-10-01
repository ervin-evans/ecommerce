package com.ecommerce.catalog.products.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String productId){
        super("El product con el id "+ productId +" no existe");
    }
}
