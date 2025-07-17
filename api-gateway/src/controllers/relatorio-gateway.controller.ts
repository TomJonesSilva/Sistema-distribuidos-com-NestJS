import { Controller, Post, Get, Body } from '@nestjs/common';
import { RelatorioService } from '../services/relatorio-gateway.service';

@Controller('relatorio')
export class RelatorioController {
  constructor(private readonly relatorioService: RelatorioService) {}

  @Post('consumidos')
  comprar(@Body() body: any) {
    return this.relatorioService.relatorioTicketsConsumidos(body);
  }

  @Post('vendidos')
  naoConsumidos(@Body() body: any) {
    return this.relatorioService.relatorioTicketsVendidos(body);
  }
}
