export class CreateUsuarioDto {
  nome: string;
  cpf: string;
  data_nascimento?: string;
  email?: string;
  senha: string;
  tipo: 'estudante' | 'funcionario';
  matricula?: string;
  salario?: number;
  data_admin?: string;
}
