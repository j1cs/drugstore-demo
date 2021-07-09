import { registerAs } from '@nestjs/config';
import * as redisStore from 'cache-manager-redis-store';

export default registerAs('cache', () => {
  return {
    store: redisStore,
    host: process.env.CACHE_HOST,
    port: Number(process.env.CACHE_PORT),
    ttl: process.env.CACHE_TTL,
    max: process.env.MAX_ITEM_IN_CACHE,
  };
});
