import { GeminiController } from '@/controllers/Gemini.controller';
import { Router } from 'express';
import multer from 'multer';

const router = Router();
const controller = new GeminiController();

const upload = multer({
  storage: multer.memoryStorage(),
});

router.post('/generate', upload.single('image'), controller.generate.bind(controller));

export default router;
