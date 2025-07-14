import { Injectable, OnModuleInit } from '@nestjs/common';
import {
  ClientProxy,
  ClientProxyFactory,
  Transport,
  ClientOptions,
} from '@nestjs/microservices';
import { CreateUsuarioDto } from '../dto/create-usuario.dto';
import { LoginDto } from '../dto/login.dto';

@Injectable()
export class UsuarioGatewayService implements OnModuleInit {
  private client: ClientProxy;

  onModuleInit() {
    this.client = ClientProxyFactory.create({
      transport: Transport.REDIS,
      options: {
        host: 'localhost',
        port: 6379,
      },
    });
  }

  getAll() {
    return this.client.send('usuario_getAll', {});
  }

  signIn(data: LoginDto) {
    return this.client.send('usuario_signin', data);
  }

  signUp(dto: CreateUsuarioDto) {
    return this.client.send('usuario_signup', dto);
  }

  buscar(cpf: string) {
    return this.client.send('usuario_buscar', cpf);
  }

  delete(cpf: string) {
    return this.client.send('usuario_delete', cpf);
  }

  edit(dto: CreateUsuarioDto) {
    return this.client.send('usuario_edit', dto);
  }
}
