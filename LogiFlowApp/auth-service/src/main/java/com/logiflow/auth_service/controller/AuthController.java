package com.logiflow.auth_service.controller;

import com.logiflow.auth_service.dto.request.*;
import com.logiflow.auth_service.dto.response.AuthResponseDto;
import com.logiflow.auth_service.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    // --- Endpoints Fase 1 (Autenticación) ---

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegistroRequestDto request) {
        return ResponseEntity.ok(authService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // --- Endpoints CRUD (Gestión) ---

    @GetMapping
    public ResponseEntity<List<AuthResponseDto>> listarUsuarios() {
        return ResponseEntity.ok(authService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthResponseDto> obtenerUsuario(@PathVariable UUID id) {
        return ResponseEntity.ok(authService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthResponseDto> actualizarUsuario(
            @PathVariable UUID id,
            @Valid @RequestBody UsuarioUpdateRequestDto request) {
        return ResponseEntity.ok(authService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable UUID id) {
        authService.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}