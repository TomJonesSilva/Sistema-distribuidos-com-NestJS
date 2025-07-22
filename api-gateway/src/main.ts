import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { ValidationPipe } from '@nestjs/common';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.useGlobalPipes(
    new ValidationPipe({
      whitelist: true, // Remove propriedades não esperadas
      forbidNonWhitelisted: true, // Dispara erro se tiver propriedades a mais
      transform: true, // Transforma payload para os DTOs corretos
    }),
  );
  await app.listen(3000);
  console.log('Gateway rodando na porta 3000');
}
bootstrap();
