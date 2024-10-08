package com.ecommerce.users.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre puede exceder los 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    private String lastname;

    @NotBlank(message = "El username obligatorio")
    @Size(max = 50, message = "El username no puede exceder los 50 caracteres")
    private String username;

    @NotBlank(message = "El correo electronico es obligatorio")
    @Size(max = 50, message = "El correo electronico no puede exceder los 100 caracteres")
    @Email(message = "El correo electronico debe ser valido")
    private String email;

    @NotBlank(message = "El password es obligatorio")
    @Size(max = 100, message = "El password no debe exceder los 100 caracteres")
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String image;
}
