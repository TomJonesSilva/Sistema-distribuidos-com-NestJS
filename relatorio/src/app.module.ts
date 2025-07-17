import { Module } from '@nestjs/common';
import { RelatorioController } from './controllers/relatorio.controller';
import { RelatorioService } from './services/relatorio.service';
import { DatabaseModule } from './dataBase/database.module';

@Module({
  imports: [DatabaseModule],
  controllers: [RelatorioController],
  providers: [RelatorioService],
})
export class AppModule {}
