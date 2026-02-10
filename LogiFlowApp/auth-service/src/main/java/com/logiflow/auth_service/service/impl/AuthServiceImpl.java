package com.logiflow.auth_service.service.impl;

import com.logiflow.auth_service.dto.mapper.UsuarioMapper;
import com.logiflow.auth_service.dto.request.*;
import com.logiflow.auth_service.dto.response.AuthResponseDto;
import com.logiflow.auth_service.model.Usuario;
import com.logiflow.auth_service.repository.UsuarioRepository;
import com.logiflow.auth_service.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public AuthResponseDto registrar(RegistroRequestDto request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El usuario ya existe");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuarioGuardado, "Usuario registrado exitosamente");
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return usuarioMapper.toResponse(usuario, "Login exitoso");
    }

    // --- CRUD ADICIONAL ---

    @Override
    @Transactional(readOnly = true)
    public List<AuthResponseDto> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(u -> usuarioMapper.toResponse(u, "Listado exitoso"))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDto obtenerPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponse(usuario, "Usuario encontrado");
    }

    @Override
    @Transactional
    public AuthResponseDto actualizar(UUID id, UsuarioUpdateRequestDto request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Actualizamos los campos usando el mapper
        usuarioMapper.updateEntity(usuario, request);

        Usuario actualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(actualizado, "Usuario actualizado correctamente");
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Usuario no existe.");
        }
        usuarioRepository.deleteById(id);
    }
}