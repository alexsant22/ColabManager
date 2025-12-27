package com.example.ColabManager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 10)
    private String sigla;

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios;
}
