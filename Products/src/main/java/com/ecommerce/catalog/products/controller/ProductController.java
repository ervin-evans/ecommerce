package com.ecommerce.catalog.products.controller;

import com.ecommerce.catalog.products.exceptions.ProductNotFoundException;
import com.ecommerce.catalog.products.model.Product;
import com.ecommerce.catalog.products.requests.ProductRequest;
import com.ecommerce.catalog.products.responses.Message;
import com.ecommerce.catalog.products.responses.MessageType;
import com.ecommerce.catalog.products.responses.ProductCustomResponse;
import com.ecommerce.catalog.products.responses.ProductResponse;
import com.ecommerce.catalog.products.service.IProductService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    private IProductService iProductService;

    /******************************************************************************************************************
     *                                              FIND ALL BY PAGE
     ******************************************************************************************************************/
    @GetMapping
    public ResponseEntity<ProductCustomResponse> findAllByPage(@RequestParam(defaultValue = "0") int page) {
        Page<Product> allProductsByPage = iProductService.findAllByPage(page);
        logger.info("Fetching all products");
        ProductCustomResponse customResponse = ProductCustomResponse.builder()
                .products(allProductsByPage.getContent())
                .totalElements(allProductsByPage.getNumberOfElements())
                .elementsPerPage(allProductsByPage.getSize())
                .totalPages(allProductsByPage.getTotalPages())
                .empty(allProductsByPage.isEmpty())
                .first(allProductsByPage.isFirst())
                .last(allProductsByPage.isLast())
                .build();
        return ResponseEntity.ok(customResponse);
    }

    /******************************************************************************************************************
     *                                              FIND BY NAME
     ******************************************************************************************************************/
    @GetMapping("/search")
    public ResponseEntity<ProductCustomResponse> findByName(@RequestParam String regex, @RequestParam("page-number") int pageNumber) {
        if (pageNumber < 0) pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, 10);
        logger.info("Recibida la solicitud de busqueda: {}", regex);
        Page<Product> page = iProductService.findProductByName(regex, pageable);
        logger.info("Se retornan {} productos", page.getNumberOfElements());
        ProductCustomResponse customResponse = ProductCustomResponse.builder()
                .products(page.getContent())
                .totalElements(page.getNumberOfElements())
                .elementsPerPage(page.getSize())
                .totalPages(page.getTotalPages())
                .empty(page.isEmpty())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
        return ResponseEntity.ok(customResponse);
    }

    /******************************************************************************************************************
     *                                          CREATE NEW PRODUCT
     ******************************************************************************************************************/

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest) {
        Product savedProduct = iProductService.createNewProduct(productRequest);
        logger.info("El producto " + savedProduct.getName() + " ha sido creado");
        Message message = Message.builder()
                .message("EL producto ha sido guardado")
                .type(MessageType.INFO)
                .build();
        ProductResponse productResponse = ProductResponse.builder()
                .product(savedProduct)
                .message(message)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);

    }

    /******************************************************************************************************************
     *                                                  UPDATE PRODUCT
     ******************************************************************************************************************/

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("productId") String productId,
            @Valid @RequestBody ProductRequest productRequest) {

        //Verificamos que el producto exista
        Product existingProduct = iProductService.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        //Actualizamos el producto
        Product updatedProduct = iProductService.updateProduct(productId, productRequest);
        logger.info("El producto ha sido actualizado");

        Message message = Message.builder()
                .message("El producto " + updatedProduct.getName() + " ha sido actualizado!")
                .type(MessageType.INFO)
                .build();
        ProductResponse response = ProductResponse.builder()
                .product(updatedProduct)
                .message(message)
                .build();

        return ResponseEntity.ok().body(response);

    }

    /******************************************************************************************************************
     *                                              DELETE PRODUCT
     ******************************************************************************************************************/

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable String productId) {
        Product product = iProductService.deleteProduct(productId);
        logger.info("El producto " + product.getName() + " ha sido eliminado");
        Message message = Message.builder()
                .message("El producto " + product.getName() + " ha sido eliminado")
                .type(MessageType.INFO)
                .build();
        ProductResponse response = ProductResponse.builder()
                .product(product)
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }
}
