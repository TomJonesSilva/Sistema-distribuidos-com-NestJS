import { Controller, Post, Body } from '@nestjs/common';
import { CardapioService } from '../services/cardapio-gateway.service';

@Controller('cardapio')
export class CardapioController {
  constructor(private readonly cardapioService: CardapioService) {}

  @Post('cadastrar')
  cadastrar(@Body() body: any) {
    return this.cardapioService.cadastrarCardapio(body);
  }

  @Post('buscar')
  buscar(@Body() body: any) {
    return this.cardapioService.buscarCardapio(body);
  }

  @Post('editar')
  editar(@Body() body: any) {
    return this.cardapioService.editarCardapio(body);
  }
}
