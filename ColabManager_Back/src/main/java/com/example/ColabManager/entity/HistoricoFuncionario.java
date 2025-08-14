package com.example.ColabManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoFuncionario {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String tipo; // Tipo de alteração

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false, updatable = false) // Não permite alteração depois
    private LocalDateTime alterado_em;

    // Executado antes de inserir no banco
    @PrePersist
    public void prePersist() {
        this.alterado_em = LocalDateTime.now();
    }

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;
}
