import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppController } from './app.controller';
import { CacheService } from './cache/cache.service';
import { CacheModule } from './cache/cache.module';
import { UserModule } from './user/user.module';
import DatabaseConfig from './config/database.config';
import CacheConfig from './config/cache.config';

@Module({
  imports: [
    ConfigModule.forRoot({
      envFilePath:
        (process.env.NODE_ENV ? `.${process.env.NODE_ENV}` : '') + '.env',
      ignoreEnvFile: !process.env.NODE_ENV,
      isGlobal: true,
      load: [DatabaseConfig, CacheConfig],
    }),
    TypeOrmModule.forRootAsync({
      inject: [ConfigService],
      useFactory: async (configService: ConfigService) => ({
        ...configService.get('database'),
      }),
    }),
    CacheModule,
    UserModule,
  ],
  controllers: [AppController],
  providers: [CacheService],
})
export class AppModule {}
