package com.example.ColabManager.dto.response;

import com.example.ColabManager.entity.Departamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoResponse {
    private Long id;
    private String nome;
    private String sigla;
    private int quantidadeFuncionarios;

    // Metodo estático para criar Response a partir da Entity
    public static DepartamentoResponse fromEntity(Departamento departamento) {
        DepartamentoResponse response = new DepartamentoResponse();

        response.setId(departamento.getId());
        response.setNome(departamento.getNome());
        response.setSigla(departamento.getSigla());
        response.setQuantidadeFuncionarios(departamento.getFuncionarios() != null ? departamento.getFuncionarios().size() : 0);

        return response;
    }
}
