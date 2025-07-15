CREATE DATABASE IF NOT EXISTS db_tickets;
USE db_tickets;

CREATE TABLE ticket_refeicao (
  ticket_codigo INT AUTO_INCREMENT PRIMARY KEY,
  tipo ENUM('almoço', 'janta'),
  data_venda TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  data_consumo TIMESTAMP NULL,
  usuario_cpf VARCHAR(14) 
);
