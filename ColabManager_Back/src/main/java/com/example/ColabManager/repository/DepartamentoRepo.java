package com.example.ColabManager.repository;

import com.example.ColabManager.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepo extends JpaRepository<Departamento, Long> {
    // Verificar se o nome do departamento já existe
    boolean existsByNome(String nome);
}
