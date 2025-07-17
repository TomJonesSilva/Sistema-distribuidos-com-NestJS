import { Controller } from '@nestjs/common';
import { MessagePattern, Payload } from '@nestjs/microservices';
import { RelatorioService } from '../services/relatorio.service';

@Controller()
export class RelatorioController {
  constructor(private readonly relatorioService: RelatorioService) {}

  @MessagePattern('relatorio_consumidos')
  async consumidos(
    @Payload() payload: { data_inicio: string; data_fim: string },
  ) {
    return this.relatorioService.consumidos(
      payload.data_inicio,
      payload.data_fim,
    );
  }

  @MessagePattern('relatorio_vendidos')
  async vendidos(
    @Payload() payload: { data_inicio: string; data_fim: string },
  ) {
    return this.relatorioService.vendidos(
      payload.data_inicio,
      payload.data_fim,
    );
  }
}
