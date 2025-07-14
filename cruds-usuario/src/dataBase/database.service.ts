import { Injectable } from '@nestjs/common';
import * as mysql from 'mysql2/promise';

@Injectable()
export class DatabaseService {
  private pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'micro_usuario',
  });

  async getConnection() {
    return this.pool;
  }
}
