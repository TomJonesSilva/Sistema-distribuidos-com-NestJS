import { Module } from '@nestjs/common';
import { OpcaoController } from './controllers/opcoes.controller';
import { OpcaoService } from './services/opcoes.service';
import { DatabaseModule } from './dataBase/database.module';

@Module({
  imports: [DatabaseModule],
  controllers: [OpcaoController],
  providers: [OpcaoService],
})
export class AppModule {}
