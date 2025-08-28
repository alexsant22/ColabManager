package com.example.ColabManager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoResponse {
    private Long id;
    private String tipo;
    private String descricao;
    private LocalDateTime alterado_em;

    // Dados básicos do funcionário (ao invés de apenas o ID)
    private FuncionarioResumidoResponse funcionario;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FuncionarioResumidoResponse {
        private Long id;
        private String nome;
        private String email;
    }
}
