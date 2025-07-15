import { Controller } from '@nestjs/common';
import { MessagePattern } from '@nestjs/microservices';
import { TicketService } from '../services/ticket.service';

@Controller()
export class TicketController {
  constructor(private readonly ticketService: TicketService) {}

  @MessagePattern('ticket_comprar')
  comprarTicket(data: any) {
    return this.ticketService.comprarTicket(data);
  }

  @MessagePattern('ticket_listar')
  listarTickets() {
    return this.ticketService.listarTickets();
  }

  @MessagePattern('ticket_nao_consumidos')
  listarNaoConsumidos(data: any) {
    return this.ticketService.listarNaoConsumidos(data);
  }

  @MessagePattern('ticket_consumir')
  consumirTicket(data: any) {
    return this.ticketService.consumirTicket(data);
  }
}
