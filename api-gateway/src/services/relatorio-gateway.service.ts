import { Injectable, OnModuleInit } from '@nestjs/common';
import {
  ClientProxy,
  ClientProxyFactory,
  Transport,
} from '@nestjs/microservices';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class RelatorioService implements OnModuleInit {
  private client: ClientProxy;

  onModuleInit() {
    this.client = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: { host: 'localhost', port: 6379 },
    });
  }

  relatorioTicketsConsumidos(data: any) {
    return firstValueFrom(this.client.send('relatorio_consumidos', data));
  }

  relatorioTicketsVendidos(data: any) {
    return firstValueFrom(this.client.send('relatorio_vendidos', data));
  }
}
