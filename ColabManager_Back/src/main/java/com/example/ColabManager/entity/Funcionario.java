package com.example.ColabManager.entity;

import com.example.ColabManager.entity.enums.StatusFuncionario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(nullable = false)
    private LocalDate data_nascimento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    @Column(nullable = false)
    private LocalDate data_admissao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFuncionario status;

    @Column(nullable = false, updatable = false) // Não permite alteração depois
    private LocalDateTime criado_em;

    // Executado antes de inserir no banco
    @PrePersist
    public void prePersist() {
        this.criado_em = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusFuncionario.ATIVO; // Define status padrão como ATIVO
        }
    }

    // Relacionamentos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    @OneToOne(mappedBy = "funcionario", fetch = FetchType.LAZY)
    private Usuario usuario;

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
    private List<HistoricoFuncionario> historicos;
}
