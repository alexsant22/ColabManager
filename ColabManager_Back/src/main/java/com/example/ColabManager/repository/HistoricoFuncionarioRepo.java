package com.example.ColabManager.repository;

import com.example.ColabManager.entity.HistoricoFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoFuncionarioRepo extends JpaRepository<HistoricoFuncionario, Long> {

    // Buscar histórico por ID do funcionário, ordenado pela data de alteração em ordem decrescente
    List<HistoricoFuncionario> findByFuncionarioIdOrderByAlteradoEmDesc(Long funcionarioId);
}
