import { Injectable, OnModuleInit } from '@nestjs/common';
import {
  ClientProxy,
  ClientProxyFactory,
  Transport,
} from '@nestjs/microservices';
import { DatabaseService } from '../dataBase/database.service';
import * as moment from 'moment';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class CardapioService implements OnModuleInit {
  private opcaoClient: ClientProxy;

  constructor(private readonly db: DatabaseService) {}

  onModuleInit() {
    this.opcaoClient = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: { host: 'localhost', port: 6379 },
    });
  }

  async buscarOpcao(codigo: number) {
    const resultado = await firstValueFrom(
      this.opcaoClient.send('opcao_buscar', { codigo }),
    );
    return resultado;
  }

  async cadastrarCardapio(body: any) {
    const {
      data_inicio,
      tipo,
      opcao_segunda,
      opcao_terca,
      opcao_quarta,
      opcao_quinta,
      opcao_sexta,
    } = body;

    const opcoes = await Promise.all([
      this.buscarOpcao(opcao_segunda),
      this.buscarOpcao(opcao_terca),
      this.buscarOpcao(opcao_quarta),
      this.buscarOpcao(opcao_quinta),
      this.buscarOpcao(opcao_sexta),
    ]);

    if (opcoes.some((op) => op?.erro)) {
      return { erro: 'Uma ou mais opções não existem' };
    }

    const conn = await this.db.getConnection();
    const [existe]: any[] = await conn.query(
      'SELECT * FROM cardapio WHERE data_inicio = ? AND tipo = ?',
      [data_inicio, tipo],
    );

    if (existe.length > 0)
      return { erro: 'Cardápio já existe para esta data e tipo' };

    await conn.query(
      'INSERT INTO cardapio (data_inicio, tipo, opcao_segunda, opcao_terca, opcao_quarta, opcao_quinta, opcao_sexta) VALUES (?, ?, ?, ?, ?, ?, ?)',
      [
        data_inicio,
        tipo,
        opcao_segunda,
        opcao_terca,
        opcao_quarta,
        opcao_quinta,
        opcao_sexta,
      ],
    );

    return { mensagem: 'Cardápio cadastrado com sucesso' };
  }

  async buscarCardapio(body: any) {
    const { data_inicio, tipo } = body;

    const conn = await this.db.getConnection();
    const [cardapios]: any[] = await conn.query(
      'SELECT * FROM cardapio WHERE data_inicio = ? AND tipo = ?',
      [data_inicio, tipo],
    );

    if (cardapios.length === 0) return { erro: 'Cardápio não encontrado' };

    const cardapio = cardapios[0];
    const opcoesDetalhadas = {
      segunda: await this.buscarOpcao(cardapio.opcao_segunda),
      terca: await this.buscarOpcao(cardapio.opcao_terca),
      quarta: await this.buscarOpcao(cardapio.opcao_quarta),
      quinta: await this.buscarOpcao(cardapio.opcao_quinta),
      sexta: await this.buscarOpcao(cardapio.opcao_sexta),
    };

    return {
      ...cardapio,
      opcoes: opcoesDetalhadas,
    };
  }

  async editarCardapio(body: any) {
    const {
      data_inicio,
      tipo,
      opcao_segunda,
      opcao_terca,
      opcao_quarta,
      opcao_quinta,
      opcao_sexta,
    } = body;

    const conn = await this.db.getConnection();
    const dataAtual = moment();
    const dataCardapio = moment(data_inicio, 'YYYY-MM-DD');

    if (dataCardapio.isBefore(dataAtual, 'week')) {
      return { erro: 'Não é possível alterar cardápios de semanas passadas' };
    }

    const [existe]: any[] = await conn.query(
      'SELECT * FROM cardapio WHERE data_inicio = ? AND tipo = ?',
      [data_inicio, tipo],
    );

    if (existe.length === 0) return { erro: 'Cardápio não encontrado' };

    await conn.query(
      'UPDATE cardapio SET opcao_segunda = ?, opcao_terca = ?, opcao_quarta = ?, opcao_quinta = ?, opcao_sexta = ? WHERE data_inicio = ? AND tipo = ?',
      [
        opcao_segunda,
        opcao_terca,
        opcao_quarta,
        opcao_quinta,
        opcao_sexta,
        data_inicio,
        tipo,
      ],
    );

    return { mensagem: 'Cardápio atualizado com sucesso' };
  }
}
