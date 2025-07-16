import { Module } from '@nestjs/common';
import { CardapioController } from './controllers/cardio.controller';
import { CardapioService } from './services/cardapio.service';
import { DatabaseModule } from './dataBase/database.module';

@Module({
  imports: [DatabaseModule],
  controllers: [CardapioController],
  providers: [CardapioService],
})
export class AppModule {}
