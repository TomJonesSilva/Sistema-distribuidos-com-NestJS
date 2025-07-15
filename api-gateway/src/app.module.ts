import { Module } from '@nestjs/common';
import { UsuarioGatewayController } from './controllers/usuario-gateway.controller';
import { UsuarioGatewayService } from './services/usuario-gateway.service';
import { OpcaoController } from './controllers/opcao-gateway.controller';
import { TicketController } from './controllers/ticket-gateway.controller';
import { CardapioController } from './controllers/cardapio-gateway.controller';
import { OpcaoService } from './services/opcao-gateway.service';
import { TicketService } from './services/ticket-gateway.service';
import { CardapioService } from './services/cardapio-gateway.service';

@Module({
  imports: [],
  controllers: [
    UsuarioGatewayController,
    OpcaoController,
    TicketController,
    CardapioController,
  ],
  providers: [
    UsuarioGatewayService,
    OpcaoService,
    TicketService,
    CardapioService,
  ],
})
export class AppModule {}
