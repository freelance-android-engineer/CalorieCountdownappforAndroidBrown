import { FoodItemController } from '@/controllers/FoodItem.controller';
import { Router } from 'express';

const router = Router();
const controller = new FoodItemController();

router.post('/', controller.create.bind(controller));
router.get('/', controller.findAll.bind(controller));
router.get('/:id', controller.findOne.bind(controller));
router.post('/search', controller.search.bind(controller));

export default router;
