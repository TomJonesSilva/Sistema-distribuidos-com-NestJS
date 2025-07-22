import { IsString, Length } from 'class-validator';

export class CpfDto {
  @IsString()
  @Length(11, 14)
  cpf: string;
}
