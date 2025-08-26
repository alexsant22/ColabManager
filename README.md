# ColabManager

Este projeto é um sistema simples de gerenciamento de funcionários para uma empresa, desenvolvido com **Spring Boot** e **JPA** para o backend, utilizando um banco de dados **MySQL**. A arquitetura foi inspirada em um protótipo inicial desenvolvido em **Python**.

## Descrição do Projeto

O projeto `ColabManager` foi concebido para gerenciar informações de funcionários, cargos e departamentos de uma empresa. Ele é dividido em duas partes principais:

1.  **Backend em Java (Spring Boot):** Uma API RESTful que lida com a lógica de negócio, persistência de dados e segurança. Utiliza o Spring Data JPA para interagir com o banco de dados e o Spring Security para autenticação e autorização.
2.  **Protótipo em Python:** Um código inicial que serve como base conceitual para o gerenciamento de funcionários, demonstrando a funcionalidade central do projeto de forma simplificada e orientada a objetos.

## Estrutura do Projeto

O repositório está organizado da seguinte forma:

* `ColabManager_Back/`: Contém todo o código-fonte do backend Java.
    * `src/main/java/`: Código principal da aplicação.
        * `com.example.ColabManager.entity/`: Entidades JPA que representam as tabelas do banco de dados (`Cargo`, `Departamento`, `Funcionario`, `HistoricoFuncionario`, `Usuario`).
        * `com.example.ColabManager.repository/`: Interfaces de repositório Spring Data JPA para operações de banco de dados.
        * `com.example.ColabManager.dto/`: Classes DTO (Data Transfer Object) para troca de dados entre camadas.
        * `com.example.ColabManager.entity.enums/`: Enums para `StatusFuncionario` e `RoleUsuario`.
    * `src/main/resources/`: Arquivos de configuração, como `application.properties`, que define as configurações do banco de dados MySQL.
    * `pom.xml`: Arquivo de configuração do Maven, gerenciando dependências como Spring Boot, Spring Data JPA, Spring Security, MySQL Connector e Lombok.
* `projetcPython/`: Contém o código-fonte do protótipo inicial em Python.
    * `Empresa.py`: Classe que gerencia uma lista de funcionários.
    * `Funcionario.py`: Classe que representa um funcionário.
    * `Main.py`: Script principal para interação com o sistema.
* `EsquemaSQL/`: Contém um script SQL de exemplo para a criação de um esquema de banco de dados.
* `LICENSE`: Informações sobre a licença do projeto (Licença MIT).

## Tecnologias Utilizadas

* **Linguagens de Programação:**
    * Java (versão 21)
    * Python
* **Backend:**
    * Spring Boot (versão 3.5.4)
    * Spring Data JPA
    * Spring Security
    * Lombok
* **Banco de Dados:**
    * MySQL
    * Hibernate
* **Gerenciamento de Dependências:**
    * Maven

## Estrutura do Banco de Dados

A estrutura do banco de dados, conforme definida nas entidades Java, inclui as seguintes tabelas:

* `Usuario`: Gerencia as informações de login do usuário, como nome de usuário, senha e função (`ADMIN`, `GESTOR`, `VISUALIZADOR`).
* `Cargo`: Armazena informações sobre os cargos, incluindo nome, nível e descrição.
* `Departamento`: Contém dados sobre os departamentos, como nome e sigla.
* `Funcionario`: A tabela principal que armazena detalhes completos dos funcionários, como nome, email, CPF, salário, data de admissão e status (`ATIVO`, `INATIVO`, `FERIAS`, `DEMITIDO`). Possui relacionamentos com `Cargo`, `Departamento` e `Usuario`.
* `HistoricoFuncionario`: Registra o histórico de alterações para cada funcionário, incluindo o tipo de alteração e a descrição.

## Licença

Este projeto é distribuído sob a Licença MIT.
