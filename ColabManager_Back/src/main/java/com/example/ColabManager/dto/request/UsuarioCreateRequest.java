package com.example.ColabManager.dto.request;

import com.example.ColabManager.entity.Usuario;
import com.example.ColabManager.entity.enums.RoleUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateRequest {
    @NotBlank(message = "Username é obrigatório")
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotNull(message = "Role é obrigatória")
    private RoleUsuario role;

    // Opcional - pode ser null
    private Long funcionario_id;

    // Metodo para converter Request em Entity
    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setUsername(this.username);
        usuario.setSenha(this.senha);
        usuario.setRole(this.role);

        // funcionario_id será tratado no service
        return usuario;
    }

    // Metodo para aplicar updates em uma Entity existente
    public void applyToEntity(Usuario usuario) {
        usuario.setUsername(this.username);
        usuario.setSenha(this.senha);
        usuario.setRole(this.role);
        // funcionario_id será tratado no service
    }
}
