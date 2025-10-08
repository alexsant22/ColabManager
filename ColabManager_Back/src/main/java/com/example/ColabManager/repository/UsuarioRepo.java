package com.example.ColabManager.repository;

import com.example.ColabManager.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepo extends JpaRepository<Usuario, Long> {
    // Metodo para verificar se um username já existe
    boolean existsByUsername(String username);
}
