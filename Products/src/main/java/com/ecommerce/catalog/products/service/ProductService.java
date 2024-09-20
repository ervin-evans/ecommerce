package com.ecommerce.catalog.products.service;

import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.repository.ProductRepository;
import com.ecommerce.catalog.products.requests.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    private static final String UPLOAD_DIR = "/tmp/uploads/";
    private static final long MAX_FILE_SIZE = 2* 1024 * 1024; //2MB
    @Override
    public Product createNewProduct(ProductRequest productRequest ) {
        Product newProduct = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        return productRepository.save(newProduct);
    }

    private String uploadImage(MultipartFile file) throws IOException{
        validateFile(file);
        if(!file.isEmpty()){
            String filename = UUID.randomUUID().toString();
            File destination = new File(UPLOAD_DIR + filename);
            file.transferTo(destination);
            //product.setImageUrl(destination.getAbsolutePath());
        }
        return "";
    }
    //validate the file type and size
    private void validateFile(MultipartFile file){
        if (!file.isEmpty()){
            if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
                throw new IllegalArgumentException("Solo se permiten archivos JPG o PNG");
            }
            if (file.getSize() > MAX_FILE_SIZE){
                throw new IllegalArgumentException("EL archivo no debe superar los 2MB");
            }
        }
    }
}
