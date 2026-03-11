import { Router } from 'express';
import foodRoutes from '@/routes/foodItem.routes';
import exerciseRoutes from '@/routes/exerciseItem.routes';
import geminiRotes from '@/routes/gemini.routes';

const router = Router();
router.use('/food', foodRoutes);
router.use('/exercise', exerciseRoutes);
router.use('/gemini', geminiRotes);

export default router;
