import { Injectable } from '@nestjs/common';
import { DatabaseService } from '../dataBase/database.service';

@Injectable()
export class TicketService {
  constructor(private readonly db: DatabaseService) {}

  async comprarTicket(data: { tipo: string; usuario: string }) {
    const conn = await this.db.getConnection();
    await conn.query(
      'INSERT INTO ticket_refeicao (tipo, data_venda, usuario_cpf) VALUES (?, NOW(), ?)',
      [data.tipo, data.usuario],
    );
    return { mensagem: 'Ticket comprado com sucesso' };
  }

  async listarTickets() {
    const conn = await this.db.getConnection();
    const [rows] = await conn.query('SELECT * FROM ticket_refeicao');
    return rows;
  }

  async listarNaoConsumidos(data: { tipo: string; usuario: string }) {
    const conn = await this.db.getConnection();

    const [rows] = await conn.query(
      'SELECT * FROM ticket_refeicao WHERE usuario_cpf = ? AND tipo = ? AND data_consumo IS NULL',
      [data.usuario, data.tipo],
    );
    return { total: (rows as any[]).length };
  }

  async consumirTicket(data: { tipo: string; usuario: string }) {
    const conn = await this.db.getConnection();
    const [rows] = await conn.query(
      'SELECT * FROM ticket_refeicao WHERE usuario_cpf = ? AND tipo = ? AND data_consumo IS NULL LIMIT 1',
      [data.usuario, data.tipo],
    );

    if ((rows as any[]).length === 0) {
      return { erro: 'Nenhum ticket disponível para consumo' };
    }

    const ticket = (rows as any[])[0];
    await conn.query(
      'UPDATE ticket_refeicao SET data_consumo = CURRENT_TIMESTAMP WHERE ticket_codigo = ?',
      [ticket.ticket_codigo],
    );
    return { mensagem: 'Ticket consumido com sucesso' };
  }
}
