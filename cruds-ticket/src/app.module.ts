import { Module } from '@nestjs/common';
import { TicketController } from './controllers/ticket.controller';
import { TicketService } from './services/ticket.service';
import { DatabaseModule } from './dataBase/database.module';

@Module({
  imports: [DatabaseModule],
  controllers: [TicketController],
  providers: [TicketService],
})
export class AppModule {}
