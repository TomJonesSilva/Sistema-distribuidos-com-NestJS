import {
  Injectable,
  OnModuleInit,
  ServiceUnavailableException,
} from '@nestjs/common';
import {
  ClientProxy,
  ClientProxyFactory,
  Transport,
  ClientOptions,
} from '@nestjs/microservices';
import { CreateUsuarioDto } from '../dto/create-usuario.dto';
import { LoginDto } from '../dto/login.dto';
import {
  Observable,
  firstValueFrom,
  timeout,
  catchError,
  throwError,
} from 'rxjs';

@Injectable()
export class UsuarioGatewayService implements OnModuleInit {
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
                'Microserviço de usuários está fora do ar',
              ),
          );
        }),
      ),
    );
  }

  onModuleInit() {
    this.client = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: {
        host: 'localhost',
        port: 6379,
      },
    });
  }

  async getAll() {
    return this.callWithTimeout(this.client.send('usuario_getAll', {}));
  }

  async signIn(data: LoginDto) {
    return this.callWithTimeout(this.client.send('usuario_signin', data));
  }

  signUp(dto: CreateUsuarioDto) {
    return this.client.send('usuario_signup', dto); // continua retornando um Observable, pois não usa timeout
  }

  async buscar(cpf: string) {
    return this.callWithTimeout(this.client.send('usuario_buscar', cpf));
  }

  async delete(cpf: string) {
    return this.callWithTimeout(this.client.send('usuario_delete', cpf));
  }

  async edit(dto: CreateUsuarioDto) {
    return this.callWithTimeout(this.client.send('usuario_edit', dto));
  }
}
