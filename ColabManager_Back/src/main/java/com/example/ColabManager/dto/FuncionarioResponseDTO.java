package com.example.ColabManager.dto;

import com.example.ColabManager.entity.enums.StatusFuncionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate data_nascimento;
    private BigDecimal salario;
    private LocalDate data_admissao;
    private StatusFuncionario status;
    private LocalDateTime criado_em;

    // Dados dos relacionamentos (nomes ao invés de IDs)
    private String cargoNome;
    private String cargoNivel;
    private String departamentoNome;
    private String departamentoSigla;
}
