package com.example.ColabManager.dto.response;

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
}
