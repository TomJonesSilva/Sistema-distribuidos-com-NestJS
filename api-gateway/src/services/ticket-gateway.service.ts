import {
  Injectable,
  OnModuleInit,
  ServiceUnavailableException,
} from '@nestjs/common';
import {
  ClientProxy,
  ClientProxyFactory,
  Transport,
} from '@nestjs/microservices';
import {
  Observable,
  firstValueFrom,
  timeout,
  catchError,
  throwError,
} from 'rxjs';

@Injectable()
export class TicketService implements OnModuleInit {
  private client: ClientProxy;

  async callWithTimeout<T>(obs$: Observable<T>, time = 3000): Promise<T> {
    return await firstValueFrom(
      obs$.pipe(
        timeout(time),
        catchError((err) => {
          console.error('Erro ao chamar microserviço:', err.message);
          return throwError(
            () =>
              new ServiceUnavailableException(
                'Microserviço de tickets está fora do ar',
              ),
          );
        }),
      ),
    );
  }

  onModuleInit() {
    this.client = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: { host: 'localhost', port: 6379 },
    });
  }

  comprarTicket(data: any) {
    return this.callWithTimeout(this.client.send('ticket_comprar', data));
  }

  listarTickets() {
    return this.callWithTimeout(this.client.send('ticket_listar', {}));
  }

  naoConsumidos(data: any) {
    return this.callWithTimeout(
      this.client.send('ticket_nao_consumidos', data),
    );
  }

  consumir(data: any) {
    return this.callWithTimeout(this.client.send('ticket_consumir', data));
  }
}
