import { registerAs } from '@nestjs/config';

export default registerAs('database', () => {
  return {
    type: process.env.DATABASE_TYPE,
    host: process.env.DATABASE_HOST,
    port: Number(process.env.DATABASE_PORT),
    username: process.env.DATABASE_USER,
    password: process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE_DB,
    synchronize: !!process.env.NODE_ENV,
    autoLoadEntities: true,
    migrationsRun: !process.env.NODE_ENV,
    logging: true,
    entities: [`${__dirname}/../**/*.entity{.ts,.js}`],
    migrations: [`${__dirname}/../migrations/**/*{.ts,.js}`],
    cli: { migrationsDir: 'src/migrations' },
  };
});
