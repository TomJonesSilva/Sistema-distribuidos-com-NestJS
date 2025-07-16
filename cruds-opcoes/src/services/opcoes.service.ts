import { Injectable } from '@nestjs/common';
import { DatabaseService } from '../dataBase/database.service';

@Injectable()
export class OpcaoService {
  constructor(private readonly db: DatabaseService) {}

  async cadastrar(data: any) {
    const { opcao1, opcao2, vegana, fast_grill, suco, sobremesa } = data;
    const conn = await this.db.getConnection();
    const [result]: any = await conn.query(
      `INSERT INTO opcao_refeicao (opcao1, opcao2, vegana, fast_grill, suco, sobremesa)
       VALUES (?, ?, ?, ?, ?, ?)`,
      [opcao1, opcao2, vegana, fast_grill, suco, sobremesa],
    );
    return { codigo_opcao: result.insertId };
  }

  async buscar(codigo: number) {
    const conn = await this.db.getConnection();
    const [result]: any = await conn.query(
      'SELECT * FROM opcao_refeicao WHERE codigo_opcao = ?',
      [codigo],
    );

    if (result.length === 0) {
      return { erro: 'Opção não existe' };
    }

    return result[0];
  }

  async editar(data: any) {
    const {
      codigo_opcao,
      opcao1,
      opcao2,
      vegana,
      fast_grill,
      suco,
      sobremesa,
    } = data;

    const opcao = await this.buscar(codigo_opcao);
    if ('erro' in opcao) {
      return { erro: opcao.erro };
    }

    const conn = await this.db.getConnection();
    await conn.query(
      `UPDATE opcao_refeicao
       SET opcao1 = ?, opcao2 = ?, vegana = ?, fast_grill = ?, suco = ?, sobremesa = ?
       WHERE codigo_opcao = ?`,
      [opcao1, opcao2, vegana, fast_grill, suco, sobremesa, codigo_opcao],
    );

    return { mensagem: 'Opção de refeição editada com sucesso.' };
  }
}
