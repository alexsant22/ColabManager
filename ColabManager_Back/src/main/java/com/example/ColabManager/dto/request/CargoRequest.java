package com.example.ColabManager.dto.request;

import com.example.ColabManager.entity.Cargo;
import com.example.ColabManager.entity.enums.NivelCargo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoRequest {
    @NotBlank(message = "Nome do cargo é obrigatório")
    @Size(max = 50, message = "Nome não pode exceder 50 caracteres")
    private String nome;

    @NotBlank(message = "Nível do cargo é obrigatório")
    @Size(max = 20, message = "Nível não pode exceder 20 caracteres")
    private String nivel;

    @NotBlank(message = "Descrição do cargo é obrigatória")
    private String descricao;

    // Método para converter para Entity
    public Cargo toEntity() {
        Cargo cargo = new Cargo();
        cargo.setNome(this.nome);
        cargo.setNivel(NivelCargo.valueOf(this.nivel.toUpperCase())); // Converte String para Enum
        cargo.setDescricao(this.descricao);
        return cargo;
    }

    // Método para aplicar updates em uma Entity existente
    public void applyToEntity(Cargo cargo) {
        cargo.setNome(this.nome);
        cargo.setNivel(NivelCargo.valueOf(this.nivel.toUpperCase())); // Converte String para Enum
        cargo.setDescricao(this.descricao);
    }
}
