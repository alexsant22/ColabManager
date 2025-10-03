# ColabManager ✨

Este projeto é um sistema simples e eficiente para o gerenciamento de funcionários de uma empresa. Ele foi desenvolvido com um robusto **Spring Boot** e **JPA** no backend, utilizando um banco de dados **MySQL** para a persistência dos dados. Sua arquitetura foi cuidadosamente inspirada em um protótipo inicial desenvolvido em **Python**. 🚀

## Descrição do Projeto 📄

O `ColabManager` foi criado para simplificar o gerenciamento de informações de colaboradores, cargos e departamentos de uma organização. Ele é composto por duas partes principais que trabalham em conjunto:

1.  **Backend em Java (Spring Boot):** Uma API RESTful poderosa que orquestra a lógica de negócio, a persistência de dados e a segurança. Utiliza o Spring Data JPA para interagir com o banco de dados e o Spring Security para garantir a autenticação e autorização robustas. 🔐
2.  **Protótipo em Python:** Um código inicial que serviu como a base conceitual para o gerenciamento de funcionários. Ele demonstra a funcionalidade central do projeto de forma simplificada e orientada a objetos, ideal para entender a essência do sistema. 💡

## Estrutura do Projeto 📂

Nosso repositório está organizado de forma clara e intuitiva:

* `ColabManager_Back/`: O coração do nosso backend Java. ☕
    * `src/main/java/`: Código principal da aplicação.
        * `com.example.ColabManager.entity/`: Nossas entidades JPA, que representam as tabelas do banco de dados: `Cargo`, `Departamento`, `Funcionario`, `HistoricoFuncionario` e `Usuario`. 📊
        * `com.example.ColabManager.repository/`: As interfaces de repositório do Spring Data JPA para todas as operações com o banco de dados. 💾
        * `com.example.ColabManager.dto/`: Classes DTO (Data Transfer Object) para uma comunicação eficiente entre as camadas. 📦
        * `com.example.ColabManager.entity.enums/`: Enums essenciais para `StatusFuncionario` e `RoleUsuario`. 🏷️
    * `src/main/resources/`: Arquivos de configuração vitais, como `application.properties`, que define as configurações do nosso banco de dados MySQL. ⚙️
    * `pom.xml`: O arquivo de configuração do Maven, que gerencia todas as nossas dependências (Spring Boot, Spring Data JPA, Spring Security, MySQL Connector, Lombok, etc.). 🛠️
* `projetcPython/`: O protótipo inicial em Python. 🐍
    * `Empresa.py`: A classe que gerencia nossa lista de funcionários. 🧑‍💻
    * `Funcionario.py`: A classe que representa um funcionário. 👤
    * `Main.py`: O script principal para interagir com o sistema. ▶️
* `EsquemaSQL/`: Contém um script SQL de exemplo para a criação do esquema do banco de dados. 📄
* `LICENSE`: Informações sobre a licença do projeto (Licença MIT). 📜

## Tecnologias Utilizadas 💻

Este projeto é construído com as seguintes tecnologias de ponta:

* **Linguagens de Programação:**
    * Java (versão 21) ☕
    * Python 🐍
* **Backend:**
    * Spring Boot (versão 3.5.4) 🍃
    * Spring Data JPA 🌿
    * Spring Security 🔒
    * Lombok 🏗️
* **Banco de Dados:**
    * PostgreSQL 🐘
    * Hibernate 🌐
* **Gerenciamento de Dependências:**
    * Maven 📦

## Estrutura do Banco de Dados 🗃️

A estrutura do nosso banco de dados, meticulosamente definida nas entidades Java, inclui as seguintes tabelas:

* `Usuario`: Gerencia as informações de login do usuário, como nome de usuário, senha e função (`ADMIN`, `GESTOR`, `VISUALIZADOR`). 🧑‍💼
* `Cargo`: Armazena informações detalhadas sobre os cargos, incluindo nome, nível e descrição. 💼
* `Departamento`: Contém dados sobre os departamentos, como nome e sigla. 🏢
* `Funcionario`: A tabela central que armazena detalhes completos dos funcionários, como nome, email, CPF, salário, data de admissão e status (`ATIVO`, `INATIVO`, `FERIAS`, `DEMITIDO`). Possui relacionamentos cruciais com `Cargo`, `Departamento` e `Usuario`. 👨‍💼👩‍💼
* `HistoricoFuncionario`: Registra o histórico completo de alterações para cada funcionário, incluindo o tipo de alteração e uma descrição detalhada. 🕰️

## Licença ⚖️

Este projeto é distribuído sob a generosa [Licença MIT](https://opensource.org/licenses/MIT). 🌟

---

Sinta-se à vontade para sugerir mais modificações!
