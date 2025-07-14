import { Injectable } from '@nestjs/common';
import { DatabaseService } from '../dataBase/database.service';
import { CreateUsuarioDto } from '../dto/create-usuario.dto';

@Injectable()
export class UsuarioService {
  constructor(private readonly db: DatabaseService) {}

  async getAll() {
    const conn = await this.db.getConnection();
    const [rows] = await conn.query('SELECT * FROM usuario');
    return rows;
  }

  async buscarUsuario(cpf: string) {
    const conn = await this.db.getConnection();
    const [usuarios] = await conn.query('SELECT * FROM usuario WHERE cpf = ?', [
      cpf,
    ]);
    if ((usuarios as any[]).length === 0) return null;

    const usuario = usuarios[0];
    const [estudantes] = await conn.query(
      'SELECT * FROM estudante WHERE cpf = ?',
      [cpf],
    );
    const [funcionarios] = await conn.query(
      'SELECT * FROM funcionario WHERE cpf = ?',
      [cpf],
    );

    if ((estudantes as any[]).length > 0) {
      return {
        ...usuario,
        tipo: 'estudante',
        matricula: estudantes[0].matricula,
      };
    } else {
      return {
        ...usuario,
        tipo: 'funcionario',
        salario: funcionarios[0].salario,
        data_admin: funcionarios[0].data_admin,
      };
    }
  }

  async signIn(body: { cpf: string; senha: string }) {
    const conn = await this.db.getConnection();
    const [usuarios] = await conn.query('SELECT * FROM usuario WHERE cpf = ?', [
      body.cpf,
    ]);
    if ((usuarios as any[]).length === 0 || usuarios[0].senha !== body.senha) {
      return { mensagem: 'Usuário e/ou senha inválidos' };
    }

    const usuario = usuarios[0];
    const [estudantes] = await conn.query(
      'SELECT * FROM estudante WHERE cpf = ?',
      [body.cpf],
    );
    const tipo = (estudantes as any[]).length > 0 ? 'estudante' : 'funcionario';
    return { usuario, tipo };
  }

  async signUp(dto: CreateUsuarioDto) {
    const conn = await this.db.getConnection();
    const [existe] = await conn.query('SELECT * FROM usuario WHERE cpf = ?', [
      dto.cpf,
    ]);
    if ((existe as any[]).length > 0) return { erro: 'cpf já existe' };

    await conn.query(
      'INSERT INTO usuario (nome, cpf, data_nascimento, email, senha, dia_cadastro) VALUES (?, ?, ?, ?, ?, NOW())',
      [dto.nome, dto.cpf, dto.data_nascimento, dto.email, dto.senha],
    );

    if (dto.tipo === 'estudante') {
      await conn.query('INSERT INTO estudante (cpf, matricula) VALUES (?, ?)', [
        dto.cpf,
        dto.matricula,
      ]);
      return { mensagem: 'aluno cadastrado com sucesso' };
    } else {
      await conn.query(
        'INSERT INTO funcionario (cpf, salario, data_admin) VALUES (?, ?, ?)',
        [dto.cpf, dto.salario, dto.data_admin],
      );
      return { mensagem: 'funcionario cadastrado com sucesso' };
    }
  }

  async deleteUser(cpf: string) {
    const conn = await this.db.getConnection();
    await conn.query('DELETE FROM usuario WHERE cpf = ?', [cpf]);
    return { mensagem: 'Usuário excluído com sucesso' };
  }

  async editUser(dto: CreateUsuarioDto) {
    const conn = await this.db.getConnection();

    if (dto.tipo === 'estudante') {
      await conn.query('UPDATE estudante SET matricula = ? WHERE cpf = ?', [
        dto.matricula,
        dto.cpf,
      ]);
    } else {
      await conn.query(
        'UPDATE funcionario SET salario = ?, data_admin = ? WHERE cpf = ?',
        [dto.salario, dto.data_admin, dto.cpf],
      );
    }

    await conn.query(
      'UPDATE usuario SET nome = ?, data_nascimento = ?, email = ?, senha = ? WHERE cpf = ?',
      [dto.nome, dto.data_nascimento, dto.email, dto.senha, dto.cpf],
    );

    return { mensagem: 'usuario atualizado' };
  }
}
