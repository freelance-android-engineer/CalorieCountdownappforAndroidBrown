import { Router } from 'express';
import foodRoutes from '@/routes/foodItem.routes';
import exerciseRoutes from '@/routes/exerciseItem.routes';

const router = Router();
router.use('/food', foodRoutes);
router.use('/exercise', exerciseRoutes);

export default router;
