import { CacheModule as Cache, Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { CacheService } from './cache.service';

@Module({
  exports: [CacheModule, CacheService],
  imports: [
    !!process.env.NODE_ENV
      ? Cache.register()
      : Cache.register({
          imports: [ConfigModule],
          inject: [ConfigService],
          useFactory: async (configService: ConfigService) => ({
            ...configService.get('cache'),
          }),
        }),
  ],
  providers: [CacheService],
})
export class CacheModule {}
