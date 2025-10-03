package com.example.ColabManager.repository;

import com.example.ColabManager.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepo extends JpaRepository<Cargo, Long> {
    // Verificar se o nome do cargo já existe
    boolean existsByNome(String nome);
}
