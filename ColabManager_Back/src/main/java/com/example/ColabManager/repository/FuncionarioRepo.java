package com.example.ColabManager.repository;

import com.example.ColabManager.entity.Funcionario;
import com.example.ColabManager.entity.enums.StatusFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepo extends JpaRepository<Funcionario, Long> {
    // Verificar se CPF já existe
    boolean existsByCpf(String cpf);

    // Verificar se Email já existe
    boolean existsByEmail(String email);

    // Buscar por cargo
    List<Funcionario> findByCargoId(Long cargoId);

    // Buscar por departamento
    List<Funcionario> findByDepartamentoId(Long departamentoId);

    // Buscar por status
    List<Funcionario> findByStatus(StatusFuncionario status);

    // Buscar por CPF
    Optional<Funcionario> findByCpf(String cpf);
}
