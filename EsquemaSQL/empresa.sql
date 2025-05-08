-- https://dbdiagram.io/d/EmpresaBD-681c98af5b2fc4582fc15303

-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS EmpresaDB;
USE EmpresaDB;

-- Tabela de empresas
CREATE TABLE Empresa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- Tabela de funcionários
CREATE TABLE Funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL,
    departamento_id INT,
    FOREIGN KEY (departamento_id) REFERENCES Departamento(id) ON DELETE SET NULL
);

-- Tabela de departamentos (criada após Funcionario para permitir FK circular)
CREATE TABLE Departamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    empresa_id INT NOT NULL,
    gerente_id INT DEFAULT NULL,
    FOREIGN KEY (empresa_id) REFERENCES Empresa(id) ON DELETE CASCADE,
    FOREIGN KEY (gerente_id) REFERENCES Funcionario(id) ON DELETE SET NULL
);

-- Ajuste da FK em Funcionario agora que Departamento existe
ALTER TABLE Funcionario
    ADD CONSTRAINT fk_departamento
    FOREIGN KEY (departamento_id) REFERENCES Departamento(id) ON DELETE SET NULL;
