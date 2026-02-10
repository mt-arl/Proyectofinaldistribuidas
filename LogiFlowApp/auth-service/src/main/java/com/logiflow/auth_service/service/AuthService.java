package com.logiflow.auth_service.service;

import com.logiflow.auth_service.dto.mapper.UsuarioMapper;
import com.logiflow.auth_service.dto.request.LoginRequestDto;
import com.logiflow.auth_service.dto.request.RegistroRequestDto;
import com.logiflow.auth_service.dto.response.AuthResponseDto;
import com.logiflow.auth_service.model.Usuario;
import com.logiflow.auth_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper; // Inyección del componente Mapper

    @Transactional
    public AuthResponseDto registrar(RegistroRequestDto request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El usuario ya existe");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 1. Usar Mapper para convertir a Entidad
        Usuario usuario = usuarioMapper.toEntity(request);

        // 2. Guardar
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 3. Usar Mapper para convertir a Response
        return usuarioMapper.toResponse(usuarioGuardado, "Usuario registrado exitosamente");
    }

    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return usuarioMapper.toResponse(usuario, "Login exitoso");
    }
}