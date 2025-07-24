import { Controller, Get, Post, Delete, Patch, Body } from '@nestjs/common';
import { UsuarioGatewayService } from '../services/usuario-gateway.service';
import { CreateUsuarioDto } from '../dto/create-usuario.dto';
import { LoginDto } from '../dto/login.dto';
import { CpfDto } from 'src/dto/cpf.dto';

@Controller('usuarios')
export class UsuarioGatewayController {
  constructor(private readonly usuarioGateway: UsuarioGatewayService) {}

  @Get()
  async getAll() {
    return this.usuarioGateway.getAll();
  }

  @Post('signin')
  async signIn(@Body() body: LoginDto) {
    return this.usuarioGateway.signIn(body);
  }

  @Post('signup')
  async signUp(@Body() dto: CreateUsuarioDto) {
    return this.usuarioGateway.signUp(dto);
  }

  @Post('buscar')
  async buscar(@Body() body: any) {
    return this.usuarioGateway.buscar(body.cpf);
  }

  @Delete()
  async delete(@Body() body: CpfDto) {
    return this.usuarioGateway.delete(body.cpf);
  }

  @Patch()
  async edit(@Body() dto: CreateUsuarioDto) {
    return this.usuarioGateway.edit(dto);
  }
}
