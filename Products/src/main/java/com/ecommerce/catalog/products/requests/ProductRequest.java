package com.ecommerce.catalog.products.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripcion es obligatoria")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "EL precio no puede ser negativo")
    private Double price;

    private String imageUrl;
}
