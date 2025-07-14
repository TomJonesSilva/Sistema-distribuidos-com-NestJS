import { Module } from '@nestjs/common';
import { UsuarioGatewayController } from './controllers/usuario-gateway.controller';
import { UsuarioGatewayService } from './services/usuario-gateway.service';

@Module({
  imports: [],
  controllers: [UsuarioGatewayController],
  providers: [UsuarioGatewayService],
})
export class AppModule {}
