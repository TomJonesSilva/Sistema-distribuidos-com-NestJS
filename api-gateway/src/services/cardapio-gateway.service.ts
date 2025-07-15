import { Injectable, OnModuleInit } from '@nestjs/common';
import {
  ClientProxy,
  ClientProxyFactory,
  Transport,
} from '@nestjs/microservices';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class CardapioService implements OnModuleInit {
  private client: ClientProxy;

  onModuleInit() {
    this.client = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: { host: 'localhost', port: 6379 },
    });
  }

  cadastrarCardapio(data: any) {
    return firstValueFrom(this.client.send('cardapio_cadastrar', data));
  }

  buscarCardapio(data: any) {
    return firstValueFrom(this.client.send('cardapio_buscar', data));
  }

  editarCardapio(data: any) {
    return firstValueFrom(this.client.send('cardapio_editar', data));
  }
}
