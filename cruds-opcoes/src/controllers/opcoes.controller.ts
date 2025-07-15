import { Controller } from '@nestjs/common';
import { MessagePattern } from '@nestjs/microservices';
import { OpcaoService } from '../services/opcoes.service';

@Controller()
export class OpcaoController {
  constructor(private readonly opcaoService: OpcaoService) {}

  @MessagePattern('opcao_cadastrar')
  cadastrar(data: any) {
    return this.opcaoService.cadastrar(data);
  }

  @MessagePattern('opcao_buscar')
  buscar(data: { codigo: number }) {
    return this.opcaoService.buscar(data.codigo);
  }

  @MessagePattern('opcao_editar')
  editar(data: any) {
    return this.opcaoService.editar(data);
  }
}
