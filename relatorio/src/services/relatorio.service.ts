import { Injectable } from '@nestjs/common';
import { DatabaseService } from '../dataBase/database.service';

@Injectable()
export class RelatorioService {
  constructor(private readonly db: DatabaseService) {}

  async consumidos(data1: string, data2: string) {
    const sql = `
      SELECT tipo, COUNT(*) AS total
      FROM ticket_refeicao
      WHERE data_consumo BETWEEN ? AND ?
      GROUP BY tipo
    `;

    const [rows]: any = await this.db.query(sql, [data1, data2]);

    const resposta = {
      almocos: 0,
      jantas: 0,
    };

    for (const row of rows) {
      if (row.tipo === 'almoço') resposta.almocos = row.total;
      if (row.tipo === 'janta') resposta.jantas = row.total;
    }

    return resposta;
  }

  async vendidos(data1: string, data2: string) {
    const sql = `
      SELECT tipo, COUNT(*) AS total
      FROM ticket_refeicao
      WHERE data_venda BETWEEN ? AND ?
      GROUP BY tipo
    `;

    const [rows]: any = await this.db.query(sql, [data1, data2]);

    const resposta = {
      almocos: 0,
      jantas: 0,
    };

    for (const row of rows) {
      if (row.tipo === 'almoço') resposta.almocos = row.total;
      if (row.tipo === 'janta') resposta.jantas = row.total;
    }

    return resposta;
  }
}
