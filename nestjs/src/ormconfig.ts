import { ConfigModule } from '@nestjs/config';
import DatabaseConfig from './config/database.config';

ConfigModule.forRoot({
  isGlobal: true,
  load: [DatabaseConfig],
});

export default DatabaseConfig();
