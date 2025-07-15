import { Injectable, OnModuleInit } from '@nestjs/common';
import {
  ClientProxy,
  ClientProxyFactory,
  Transport,
} from '@nestjs/microservices';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class OpcaoService implements OnModuleInit {
  private client: ClientProxy;

  onModuleInit() {
    this.client = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: { host: 'localhost', port: 6379 },
    });
  }

  cadastrarOpcao(data: any) {
    return firstValueFrom(this.client.send('opcao_cadastrar', data));
  }

  buscarOpcao(codigo: number) {
    return firstValueFrom(this.client.send('opcao_buscar', { codigo }));
  }

  editarOpcao(data: any) {
    return firstValueFrom(this.client.send('opcao_editar', data));
  }
}
