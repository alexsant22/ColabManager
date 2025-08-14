package com.example.ColabManager.repository;

import com.example.ColabManager.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepo extends JpaRepository<Funcionario, Long> {
}
