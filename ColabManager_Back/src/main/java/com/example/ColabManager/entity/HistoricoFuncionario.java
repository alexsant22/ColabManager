package com.example.ColabManager.entity;

import com.example.ColabManager.entity.enums.TipoHistorico;
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

    @Column(nullable = false, updatable = false)
    private LocalDateTime alterado_em;

    @PrePersist
    public void prePersist() {
        this.alterado_em = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;
}