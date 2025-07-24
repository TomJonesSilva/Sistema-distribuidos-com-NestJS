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
export class RelatorioService implements OnModuleInit {
  private client: ClientProxy;

  async callWithTimeout<T>(obs$: Observable<T>, time = 3000): Promise<T> {
    return await firstValueFrom(
      obs$.pipe(
        timeout(time),
        catchError((err) =>
          throwError(
            () =>
              new ServiceUnavailableException(
                'Microserviço de relatórios está fora do ar',
              ),
          ),
        ),
      ),
    );
  }

  onModuleInit() {
    this.client = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: { host: 'localhost', port: 6379 },
    });
  }

  relatorioTicketsConsumidos(data: any) {
    return this.callWithTimeout(this.client.send('relatorio_consumidos', data));
  }

  relatorioTicketsVendidos(data: any) {
    return this.callWithTimeout(this.client.send('relatorio_vendidos', data));
  }
}
