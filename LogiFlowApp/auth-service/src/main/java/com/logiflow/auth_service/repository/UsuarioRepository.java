package com.logiflow.auth_service.repository;

import com.logiflow.auth_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID; // Importante

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> { // UUID aquí
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}