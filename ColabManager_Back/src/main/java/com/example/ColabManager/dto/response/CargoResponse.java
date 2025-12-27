package com.example.ColabManager.dto.response;

import com.example.ColabManager.entity.Cargo;
import com.example.ColabManager.entity.enums.NivelCargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoResponse {
    private Long id;
    private String nome;
    private NivelCargo nivel;
    private String descricao;
    private int quantidadeFuncionarios;

    // Método estático para criar Response a partir da Entity
    public static CargoResponse fromEntity(Cargo cargo) {
        CargoResponse response = new CargoResponse();
        response.setId(cargo.getId());
        response.setNome(cargo.getNome());
        response.setNivel(cargo.getNivel());
        response.setDescricao(cargo.getDescricao());
        response.setQuantidadeFuncionarios(cargo.getFuncionarios() != null ? cargo.getFuncionarios().size() : 0);

        return response;
    }
}