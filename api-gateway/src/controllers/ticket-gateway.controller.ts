import { Controller, Post, Get, Body } from '@nestjs/common';
import { TicketService } from '../services/ticket-gateway.service';

@Controller('ticket')
export class TicketController {
  constructor(private readonly ticketService: TicketService) {}

  @Post('comprar')
  comprar(@Body() body: any) {
    return this.ticketService.comprarTicket(body);
  }

  @Get('listar')
  listar() {
    return this.ticketService.listarTickets();
  }

  @Post('nao-consumidos')
  naoConsumidos(@Body() body: any) {
    return this.ticketService.naoConsumidos(body);
  }

  @Post('consumir')
  consumir(@Body() body: any) {
    return this.ticketService.consumir(body);
  }
}
