package com.logiflow.auth_service.service;

import com.logiflow.auth_service.dto.request.LoginRequestDto;
import com.logiflow.auth_service.dto.request.RegistroRequestDto;
import com.logiflow.auth_service.dto.request.UsuarioUpdateRequestDto;
import com.logiflow.auth_service.dto.response.AuthResponseDto;
import java.util.List;
import java.util.UUID;

public interface IAuthService {
    AuthResponseDto registrar(RegistroRequestDto request);
    AuthResponseDto login(LoginRequestDto request);

    // Nuevos métodos CRUD
    List<AuthResponseDto> listarTodos();
    AuthResponseDto obtenerPorId(UUID id);
    AuthResponseDto actualizar(UUID id, UsuarioUpdateRequestDto request);
    void eliminar(UUID id);
}