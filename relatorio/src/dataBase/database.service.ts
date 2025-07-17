import { Injectable } from '@nestjs/common';
import * as mysql from 'mysql2/promise';

@Injectable()
export class DatabaseService {
  private pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'db_tickets',
  });

  async query(sql: string, params: any[]) {
    return this.pool.query(sql, params);
  }
}
