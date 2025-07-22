import { Controller } from '@nestjs/common';
import { MessagePattern, Payload } from '@nestjs/microservices';
import { UsuarioService } from '../services/usuario.service';
import { CreateUsuarioDto } from '../dto/create-usuario.dto';
import { LoginDto } from '../dto/login.dto';
import { validate } from 'class-validator';
import { plainToInstance } from 'class-transformer';

@Controller()
export class UsuarioMessageHandler {
  constructor(private readonly usuarioService: UsuarioService) {}

  @MessagePattern('usuario_getAll')
  getAll() {
    return this.usuarioService.getAll();
  }

  @MessagePattern('usuario_signin')
  async signIn(@Payload() body: any) {
    const dto = plainToInstance(LoginDto, body);
    const errors = await validate(dto);

    if (errors.length > 0) {
      return {
        erro: 'Dados inválidos',
        detalhes: errors.map((e) => ({
          campo: e.property,
          erros: Object.values(e.constraints || {}),
        })),
      };
    }

    return this.usuarioService.signIn(dto);
  }

  @MessagePattern('usuario_signup')
  async signUp(@Payload() body: any) {
    const dto = plainToInstance(CreateUsuarioDto, body);
    const errors = await validate(dto);

    if (errors.length > 0) {
      return {
        erro: 'Dados inválidos',
        detalhes: errors.map((e) => ({
          campo: e.property,
          erros: Object.values(e.constraints || {}),
        })),
      };
    }

    return this.usuarioService.signUp(dto);
  }

  @MessagePattern('usuario_buscar')
  buscar(@Payload() cpf: string) {
    return this.usuarioService.buscarUsuario(cpf);
  }

  @MessagePattern('usuario_delete')
  delete(@Payload() cpf: string) {
    return this.usuarioService.deleteUser(cpf);
  }

  @MessagePattern('usuario_edit')
  async edit(@Payload() body: any) {
    const dto = plainToInstance(CreateUsuarioDto, body);
    const errors = await validate(dto);

    if (errors.length > 0) {
      return {
        erro: 'Dados inválidos',
        detalhes: errors.map((e) => ({
          campo: e.property,
          erros: Object.values(e.constraints || {}),
        })),
      };
    }

    return this.usuarioService.editUser(dto);
  }
}
