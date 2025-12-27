package com.example.ColabManager.dto.response;

import com.example.ColabManager.entity.Funcionario;
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
public class FuncionarioResponse {
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

    // Método estático para criar Response a partir da Entity
    public static FuncionarioResponse fromEntity(Funcionario funcionario) {
        FuncionarioResponse response = new FuncionarioResponse();
        response.setId(funcionario.getId());
        response.setNome(funcionario.getNome());
        response.setEmail(funcionario.getEmail());
        response.setCpf(funcionario.getCpf());
        response.setData_nascimento(funcionario.getData_nascimento());
        response.setSalario(funcionario.getSalario());
        response.setData_admissao(funcionario.getData_admissao());
        response.setStatus(funcionario.getStatus());
        response.setCriado_em(funcionario.getCriado_em());

        if (funcionario.getCargo() != null) {
            response.setCargoNome(funcionario.getCargo().getNome());
            response.setCargoNivel(funcionario.getCargo().getNivel().name());
        }

        if (funcionario.getDepartamento() != null) {
            response.setDepartamentoNome(funcionario.getDepartamento().getNome());
            response.setDepartamentoSigla(funcionario.getDepartamento().getSigla());
        }

        return response;
    }
}
