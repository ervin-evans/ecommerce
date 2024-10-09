package com.ecommerce.users.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {

    private UUID id;
    @NotBlank(message = "El ROLE es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String name;
}
