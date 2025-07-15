import { Module } from '@nestjs/common';
import { OpcaoController } from './controllers/opcoes.controller';
import { OpcaoService } from './services/opcoes.service';

@Module({
  imports: [],
  controllers: [OpcaoController],
  providers: [OpcaoService],
})
export class AppModule {}
