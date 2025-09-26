import express, { Application } from 'express';
import routes from '@/routes';
import cors from 'cors';
import cookieParser from 'cookie-parser';

export function createApp(): Application {
  const app: Application = express();

  app.use(
    cors({
      origin: true,
      credentials: true,
    })
  );
  app.use(express.json());
  app.use(cookieParser());
  app.get('/', (req, res) => {
    res.send('Welcome to the Server!');
  });

  app.use('/api/v1', routes);

  return app;
}
