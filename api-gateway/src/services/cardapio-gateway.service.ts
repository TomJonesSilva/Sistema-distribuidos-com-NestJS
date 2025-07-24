import {
  HttpException,
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
  TimeoutError,
} from 'rxjs';

@Injectable()
export class CardapioService implements OnModuleInit {
  private client: ClientProxy;

  async callWithTimeout<T>(obs$: Observable<T>, time = 3000): Promise<T> {
    return await firstValueFrom(
      obs$.pipe(
        timeout(time),
        catchError((err) => {
          if (err instanceof TimeoutError) {
            return throwError(
              () =>
                new ServiceUnavailableException(
                  'Microserviço de cardápio está fora do ar',
                ),
            );
          }
          return throwError(() => err);
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

  cadastrarCardapio(data: any) {
    return this.callWithTimeout(this.client.send('cardapio_cadastrar', data));
  }

  buscarCardapio(data: any) {
    return this.callWithTimeout(this.client.send('cardapio_buscar', data));
  }

  editarCardapio(data: any) {
    return this.callWithTimeout(this.client.send('cardapio_editar', data));
  }
}
