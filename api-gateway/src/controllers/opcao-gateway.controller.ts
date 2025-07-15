import { Controller, Post, Body } from '@nestjs/common';
import { OpcaoService } from '../services/opcao-gateway.service';

@Controller('opcao')
export class OpcaoController {
  constructor(private readonly opcaoService: OpcaoService) {}

  @Post('cadastrar')
  cadastrar(@Body() body: any) {
    return this.opcaoService.cadastrarOpcao(body);
  }

  @Post('buscar')
  buscar(@Body('codigo') codigo: number) {
    return this.opcaoService.buscarOpcao(codigo);
  }

  @Post('editar')
  editar(@Body() body: any) {
    return this.opcaoService.editarOpcao(body);
  }
}
