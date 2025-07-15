import { Module } from '@nestjs/common';
import { UsuarioMessageHandler } from './controllers/usuario.controller';
import { UsuarioService } from './services/usuario.service';
import { DatabaseModule } from './dataBase/database.module';

@Module({
  imports: [DatabaseModule],
  controllers: [UsuarioMessageHandler],
  providers: [UsuarioService],
})
export class AppModule {}
