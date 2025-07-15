CREATE DATABASE IF NOT EXISTS db_cardapio;
USE db_cardapio;

CREATE TABLE cardapio (
  data_inicio DATE NOT NULL,
  tipo ENUM('almoço', 'janta') NOT NULL,
  opcao_segunda INT,
  opcao_terca INT,
  opcao_quarta INT,
  opcao_quinta INT,
  opcao_sexta INT,
  PRIMARY KEY (data_inicio, tipo)
);
