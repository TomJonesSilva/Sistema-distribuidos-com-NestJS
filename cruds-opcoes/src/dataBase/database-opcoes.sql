CREATE DATABASE IF NOT EXISTS db_opcoes;
USE db_opcoes;

CREATE TABLE opcao_refeicao (
  codigo_opcao INT AUTO_INCREMENT PRIMARY KEY,
  opcao1 VARCHAR(30),
  opcao2 VARCHAR(30),
  vegana VARCHAR(30),
  fast_grill VARCHAR(30),
  suco VARCHAR(30),
  sobremesa VARCHAR(30)
);
