import { IsString, Length } from 'class-validator';

export class LoginDto {
  @IsString()
  @Length(11, 14)
  cpf: string;

  @IsString()
  senha: string;
}
