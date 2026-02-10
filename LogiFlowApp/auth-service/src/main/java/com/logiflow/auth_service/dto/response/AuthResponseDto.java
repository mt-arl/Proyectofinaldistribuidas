package com.logiflow.auth_service.dto.response;

import com.logiflow.auth_service.enums.Rol;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class AuthResponseDto {
    private UUID id; // Cambiado a UUID
    private String username;
    private String nombre;
    private String email;
    private Rol rol;
    private String mensaje;
}