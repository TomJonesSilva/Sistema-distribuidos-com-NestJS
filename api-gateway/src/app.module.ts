import { Module } from '@nestjs/common';
import { UsuarioGatewayController } from './controllers/usuario-gateway.controller';
import { UsuarioGatewayService } from './services/usuario-gateway.service';
import { OpcaoController } from './controllers/opcao-gateway.controller';
import { TicketController } from './controllers/ticket-gateway.controller';
import { CardapioController } from './controllers/cardapio-gateway.controller';
import { OpcaoService } from './services/opcao-gateway.service';
import { TicketService } from './services/ticket-gateway.service';
import { CardapioService } from './services/cardapio-gateway.service';
import { RelatorioController } from './controllers/relatorio-gateway.controller';
import { RelatorioService } from './services/relatorio-gateway.service';

@Module({
  imports: [],
  controllers: [
    UsuarioGatewayController,
    OpcaoController,
    TicketController,
    CardapioController,
    RelatorioController,
  ],
  providers: [
    UsuarioGatewayService,
    OpcaoService,
    TicketService,
    CardapioService,
    RelatorioService,
  ],
})
export class AppModule {}
