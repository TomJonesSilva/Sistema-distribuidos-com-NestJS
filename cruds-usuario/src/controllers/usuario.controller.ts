import { Controller } from '@nestjs/common';
import { MessagePattern } from '@nestjs/microservices';
import { UsuarioService } from '../services/usuario.service';
import { CreateUsuarioDto } from '../dto/create-usuario.dto';
import { LoginDto } from '../dto/login.dto';

@Controller()
export class UsuarioMessageHandler {
  constructor(private readonly usuarioService: UsuarioService) {}

  @MessagePattern('usuario_getAll')
  getAll() {
    return this.usuarioService.getAll();
  }

  @MessagePattern('usuario_signin')
  signIn(body: LoginDto) {
    return this.usuarioService.signIn(body);
  }

  @MessagePattern('usuario_signup')
  signUp(dto: CreateUsuarioDto) {
    return this.usuarioService.signUp(dto);
  }

  @MessagePattern('usuario_buscar')
  buscar(cpf: string) {
    return this.usuarioService.buscarUsuario(cpf);
  }

  @MessagePattern('usuario_delete')
  delete(cpf: string) {
    return this.usuarioService.deleteUser(cpf);
  }

  @MessagePattern('usuario_edit')
  edit(dto: CreateUsuarioDto) {
    return this.usuarioService.editUser(dto);
  }
}
