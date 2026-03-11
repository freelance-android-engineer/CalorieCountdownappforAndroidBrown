import http from 'http';
import { env } from '@/env';
import { createApp } from '@/app';
import { logger } from '@/logger';

async function main() {
  try {
    const PORT: number = +(env.PORT ?? 3000);
    const server = http.createServer(createApp());

    server.listen(PORT, () => {
      logger.info(`Server listening on port: ${PORT}`);
    });
  } catch (error) {
    logger.error(`Error starting server`, error);
  }
}

main();
