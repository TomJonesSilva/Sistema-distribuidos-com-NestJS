// src/usuario/dtos/create-usuario.dto.ts
import {
  IsString,
  IsEmail,
  IsOptional,
  IsIn,
  IsDateString,
  IsNumber,
  Length,
} from 'class-validator';

export class CreateUsuarioDto {
  @IsString()
  @Length(2, 100)
  nome: string;

  @IsString()
  @Length(11, 14)
  cpf: string;

  @IsOptional()
  @IsDateString()
  data_nascimento?: string;

  @IsOptional()
  @IsEmail()
  email?: string;

  @IsString()
  @Length(6, 100)
  senha: string;

  @IsIn(['estudante', 'funcionario'])
  tipo: 'estudante' | 'funcionario';

  @IsOptional()
  @IsString()
  matricula?: string;

  @IsOptional()
  @IsNumber()
  salario?: number;

  @IsOptional()
  @IsDateString()
  data_admin?: string;
}
