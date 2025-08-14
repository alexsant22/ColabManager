package com.example.ColabManager.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioUpdateRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF deve estar no formato 000.000.000-00")
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate data_nascimento;

    @NotNull(message = "Salário é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salário deve ser positivo")
    private BigDecimal salario;

    @NotNull(message = "Data de admissão é obrigatória")
    private LocalDate data_admissao;

    @NotNull(message = "Cargo é obrigatório")
    private Long cargo_id;

    @NotNull(message = "Departamento é obrigatório")
    private Long departamento_id;
}