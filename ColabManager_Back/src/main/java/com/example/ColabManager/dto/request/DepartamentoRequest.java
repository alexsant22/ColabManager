package com.example.ColabManager.dto.request;

import com.example.ColabManager.entity.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoRequest {
    @NotBlank(message = "Nome do departamento é obrigatório")
    @Size(max = 50, message = "Nome não pode exceder 50 caracteres")
    private String nome;

    @NotBlank(message = "Sigla do departamento é obrigatória")
    @Size(max = 10, message = "Sigla não pode exceder 10 caracteres")
    private String sigla;

    // Metodo para converter para Entity
    public Departamento toEntity() {
        Departamento departamento = new Departamento();
        departamento.setNome(this.nome);
        departamento.setSigla(this.sigla);
        return departamento;
    }

    // Metodo para aplicar updates em uma Entity existente
    public void applyToEntity(Departamento departamento) {
        departamento.setNome(this.nome);
        departamento.setSigla(this.sigla);
    }
}
