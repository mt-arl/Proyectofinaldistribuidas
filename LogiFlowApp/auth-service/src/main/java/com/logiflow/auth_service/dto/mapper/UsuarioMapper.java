package com.logiflow.auth_service.dto.mapper;

import com.logiflow.auth_service.dto.request.RegistroRequestDto;
import com.logiflow.auth_service.dto.request.UsuarioUpdateRequestDto;
import com.logiflow.auth_service.dto.response.AuthResponseDto;
import com.logiflow.auth_service.enums.Rol;
import com.logiflow.auth_service.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    // 1. Request -> Entidad (Registro)
    public Usuario toEntity(RegistroRequestDto dto) {
        if (dto == null) return null;

        return Usuario.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .rol(dto.getRol() != null ? dto.getRol() : Rol.CLIENTE)
                .build();
    }

    // 2. Método para actualizar Entidad existente con datos del DTO
    public void updateEntity(Usuario usuarioExistente, UsuarioUpdateRequestDto dto) {
        if (dto == null) return;

        if (dto.getNombre() != null) usuarioExistente.setNombre(dto.getNombre());
        if (dto.getEmail() != null) usuarioExistente.setEmail(dto.getEmail());
        if (dto.getRol() != null) usuarioExistente.setRol(dto.getRol());
    }

    // 3. Entidad -> Response
    public AuthResponseDto toResponse(Usuario usuario, String mensaje) {
        if (usuario == null) return null;

        return AuthResponseDto.builder()
                .id(usuario.getId()) // UUID
                .username(usuario.getUsername())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .mensaje(mensaje)
                .build();
    }
}