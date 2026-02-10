package com.logiflow.auth_service.dto.request;

import com.logiflow.auth_service.enums.Rol;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UsuarioUpdateRequestDto {
    private String nombre;

    @Email(message = "El email debe ser válido")
    private String email;

    private Rol rol;
}