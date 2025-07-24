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
export class OpcaoService implements OnModuleInit {
  private client: ClientProxy;

  async callWithTimeout<T>(obs$: Observable<T>, time = 3000): Promise<T> {
    return await firstValueFrom(
      obs$.pipe(
        timeout(time),
        catchError((err) =>
          throwError(
            () =>
              new ServiceUnavailableException(
                'Microserviço de opções está fora do ar',
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

  cadastrarOpcao(data: any) {
    return this.callWithTimeout(this.client.send('opcao_cadastrar', data));
  }

  buscarOpcao(codigo: number) {
    return this.callWithTimeout(this.client.send('opcao_buscar', { codigo }));
  }

  editarOpcao(data: any) {
    return this.callWithTimeout(this.client.send('opcao_editar', data));
  }
}
