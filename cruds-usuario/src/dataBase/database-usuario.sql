CREATE DATABASE IF NOT EXISTS db_usuario;
USE db_usuario;

CREATE TABLE usuario (
  nome varchar(30) NOT NULL,
  cpf varchar(14) NOT NULL,
  data_nascimento date NOT NULL,
  email varchar(30) NOT NULL,
  senha varchar(30) NOT NULL,
  dia_cadastro timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`cpf`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE funcionario (
  cpf varchar(14) NOT NULL,
  salario decimal(10,2) DEFAULT NULL,
  data_admin date DEFAULT NULL,
  PRIMARY KEY (`cpf`),
  CONSTRAINT `funcionario_ibfk_1` FOREIGN KEY (`cpf`) REFERENCES `usuario` (`cpf`) ON DELETE CASCADE
);

CREATE TABLE estudante (
  cpf varchar(14) NOT NULL,
  matricula char(9) NOT NULL,
  PRIMARY KEY (`cpf`),
  CONSTRAINT `estudante_ibfk_1` FOREIGN KEY (`cpf`) REFERENCES `usuario` (`cpf`) ON DELETE CASCADE
);


INSERT INTO usuario (nome, cpf, data_nascimento, email, senha, dia_cadastro)
VALUES ('carlos', '12345678912', '1990-01-01', 'email3@example.com', 'senha123', CURRENT_TIMESTAMP());

INSERT INTO funcionario (cpf, salario, data_admin)
VALUES ('12345678912', 3000.00, '2024-02-24');

INSERT INTO usuario (nome, cpf, data_nascimento, email, senha, dia_cadastro)
VALUES ('Ana Silva', '98765432100', '1985-05-10', 'ana.silva@example.com', 'senhaFuncionario', CURRENT_TIMESTAMP());

INSERT INTO funcionario (cpf, salario, data_admin)
VALUES ('98765432100', 4500.00, '2023-01-15');

INSERT INTO usuario (nome, cpf, data_nascimento, email, senha, dia_cadastro)
VALUES ('Bruno Costa', '11223344550', '2000-11-20', 'bruno.costa@example.com', 'senhaEstudante', CURRENT_TIMESTAMP());

INSERT INTO estudante (cpf, matricula)
VALUES ('11223344550', '202200001');





