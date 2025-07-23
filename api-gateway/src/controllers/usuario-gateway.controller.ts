import { Controller, Get, Post, Delete, Patch, Body } from '@nestjs/common';
import { UsuarioGatewayService } from '../services/usuario-gateway.service';
import { CreateUsuarioDto } from '../dto/create-usuario.dto';
import { LoginDto } from '../dto/login.dto';
import { firstValueFrom } from 'rxjs';
import { CpfDto } from 'src/dto/cpf.dto';

@Controller('usuarios')
export class UsuarioGatewayController {
  constructor(private readonly usuarioGateway: UsuarioGatewayService) {}

  @Get()
  async getAll() {
    return firstValueFrom(this.usuarioGateway.getAll());
  }

  @Post('signin')
  async signIn(@Body() body: LoginDto) {
    return firstValueFrom(this.usuarioGateway.signIn(body));
  }

  @Post('signup')
  async signUp(@Body() dto: CreateUsuarioDto) {
    return firstValueFrom(this.usuarioGateway.signUp(dto));
  }

  @Post('buscar')
  async buscar(@Body() body: any) {
    console.log(body.cpf);
    return firstValueFrom(this.usuarioGateway.buscar(body.cpf));
  }

  @Delete()
  async delete(@Body() body: CpfDto) {
    return firstValueFrom(this.usuarioGateway.delete(body.cpf));
  }

  @Patch()
  async edit(@Body() dto: CreateUsuarioDto) {
    return firstValueFrom(this.usuarioGateway.edit(dto));
  }
}
