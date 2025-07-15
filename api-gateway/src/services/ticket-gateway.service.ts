import { Injectable, OnModuleInit } from '@nestjs/common';
import {
  ClientProxy,
  ClientProxyFactory,
  Transport,
} from '@nestjs/microservices';
import { firstValueFrom } from 'rxjs';

@Injectable()
export class TicketService implements OnModuleInit {
  private client: ClientProxy;

  onModuleInit() {
    this.client = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: { host: 'localhost', port: 6379 },
    });
  }

  comprarTicket(data: any) {
    return firstValueFrom(this.client.send('ticket_comprar', data));
  }

  listarTickets() {
    return firstValueFrom(this.client.send('ticket_listar', {}));
  }

  naoConsumidos(data: any) {
    return firstValueFrom(this.client.send('ticket_nao_consumidos', data));
  }

  consumir(data: any) {
    return firstValueFrom(this.client.send('ticket_consumir', data));
  }
}
