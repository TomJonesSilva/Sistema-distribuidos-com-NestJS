import { Module } from '@nestjs/common';
import { CardapioController } from './controllers/cardio.controller';
import { CardapioService } from './services/cardapio.service';

@Module({
  imports: [],
  controllers: [CardapioController],
  providers: [CardapioService],
})
export class AppModule {}
