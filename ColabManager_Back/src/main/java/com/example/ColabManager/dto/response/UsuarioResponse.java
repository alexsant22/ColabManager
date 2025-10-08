package com.example.ColabManager.dto.response;

import com.example.ColabManager.entity.Usuario;
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

    // Metodo estático para criar Response a partir da Entity
    public static UsuarioResponse fromEntity(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setRole(usuario.getRole());

        // Incluir dados do funcionário vinculado, se existir
        if (usuario.getFuncionario() != null) {
            FuncionarioResumidoResponse funcResponse = new FuncionarioResumidoResponse();
            funcResponse.setId(usuario.getFuncionario().getId());
            funcResponse.setNome(usuario.getFuncionario().getNome());
            funcResponse.setEmail(usuario.getFuncionario().getEmail());
            response.setFuncionario(funcResponse);
        }

        return response;
    }
}
