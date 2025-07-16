import { Controller } from '@nestjs/common';
import { MessagePattern, Payload } from '@nestjs/microservices';
import { CardapioService } from '../services/cardapio.service';

@Controller()
export class CardapioController {
  constructor(private readonly cardapioService: CardapioService) {}

  @MessagePattern('cardapio_cadastrar')
  cadastrar(@Payload() body: any) {
    return this.cardapioService.cadastrarCardapio(body);
  }

  @MessagePattern('cardapio_buscar')
  buscar(@Payload() body: any) {
    return this.cardapioService.buscarCardapio(body);
  }

  @MessagePattern('cardapio_editar')
  editar(@Payload() body: any) {
    return this.cardapioService.editarCardapio(body);
  }
}
