package com.example.ColabManager.dto.response;

import com.example.ColabManager.entity.enums.RoleUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String username;
    private RoleUsuario role;

    // Dados básicos do funcionário vinculado (se existir)
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
