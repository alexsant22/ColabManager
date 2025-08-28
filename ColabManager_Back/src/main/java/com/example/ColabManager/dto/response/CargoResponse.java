package com.example.ColabManager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoResponse {
    private Long id;
    private String nome;
    private String nivel;
    private String descricao;
    private int quantidadeFuncionarios;
}
