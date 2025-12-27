package com.example.ColabManager.entity;

import com.example.ColabManager.entity.enums.NivelCargo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelCargo nivel;

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios;
}
