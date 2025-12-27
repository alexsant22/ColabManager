package com.example.ColabManager.entity;

import com.example.ColabManager.entity.enums.TipoHistorico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoFuncionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoHistorico tipo;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "alterado_em", nullable = false, updatable = false)
    private LocalDateTime alteradoEm;

    @PrePersist
    public void prePersist() {
        this.alteradoEm = LocalDateTime.now();
    }

    // Relacionamentos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    @JsonIgnore // Evita loop infinito no JSON
    private Funcionario funcionario;
}