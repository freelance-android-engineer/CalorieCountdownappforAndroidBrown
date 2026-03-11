import prisma from '@/config/prisma';
import { foodItemSchema } from '@/validations/foodItem.validation';
import { Request, Response } from 'express';

export class FoodItemController {
  async create(req: Request, res: Response): Promise<void> {
    try {
      const parsedData = foodItemSchema.parse(req.body);

      // Check for duplicate name
      const existingItem = await prisma.foodItem.findFirst({
        where: { food_item_name: { equals: parsedData.food_item_name, mode: 'insensitive' } },
      });

      if (existingItem) {
        res.status(409).json({
          success: false,
          message: 'A food item with this name already exists',
        });
        return;
      }

      const foodItem = await prisma.foodItem.create({
        data: parsedData,
      });
      res.status(201).json({
        success: true,
        message: 'Food item created successfully',
        data: foodItem,
      });
    } catch (error: any) {
      res.status(500).json({
        success: false,
        message: error.message || 'Internal server error',
      });
    }
  }

  async findAll(req: Request, res: Response): Promise<void> {
    try {
      const { $filter, $orderby, $top, $skip } = req.query;

      // Basic OData-like parsing
      let where: any = {};
      if ($filter && typeof $filter === 'string') {
        const [field, operator, value] = $filter.split(' ');
        const numericValue = parseFloat(value);
        switch (operator) {
          case 'eq':
            where[field] = numericValue || value;
            break;
          case 'gt':
            where[field] = { gt: numericValue };
            break;
          case 'lt':
            where[field] = { lt: numericValue };
            break;
        }
      }

      let orderBy: any = undefined;
      if ($orderby && typeof $orderby === 'string') {
        const [field, direction] = $orderby.split(' ');
        orderBy = { [field]: direction?.toLowerCase() === 'desc' ? 'desc' : 'asc' };
      }

      const take = $top ? parseInt($top as string, 10) : 20;
      const skip = $skip ? parseInt($skip as string, 10) : 0;

      const [data, total] = await Promise.all([
        prisma.foodItem.findMany({ where, orderBy, skip, take }),
        prisma.foodItem.count({ where }),
      ]);

      res.json({
        success: true,
        message: 'Food items fetched successfully',
        count: data.length,
        total,
        data,
      });
    } catch (error: any) {
      res.status(500).json({
        success: false,
        message: error.message || 'Internal server error',
      });
    }
  }

  async findOne(req: Request, res: Response): Promise<void> {
    try {
      const id = parseInt(req.params.id);
      const foodItem = await prisma.foodItem.findUnique({ where: { id } });
      if (!foodItem) {
        res.status(404).json({ success: false, message: 'Food item not found' });
        return;
      }
      res.json({ success: true, message: 'Food item fetched successfully', data: foodItem });
    } catch (error: any) {
      res.status(500).json({
        success: false,
        message: error.message || 'Internal server error',
      });
    }
  }

  async search(req: Request, res: Response): Promise<void> {
    try {
      const { '@context': context, '@type': type, query } = req.body;

      if (!context || !type || !query || typeof query !== 'object') {
        res.status(400).json({
          success: false,
          message: 'Invalid JSON-LD format. Must include @context, @type, and query object.',
        });
        return;
      }

      const where: any = {};
      for (const [key, value] of Object.entries(query)) {
        if (typeof value === 'string') {
          where[key] = { contains: value, mode: 'insensitive' };
        } else if (typeof value === 'number') {
          where[key] = value;
        }
      }

      const results = await prisma.foodItem.findMany({ where });

      res.json({
        '@context': context,
        '@type': 'SearchResultsPage',
        success: true,
        totalResults: results.length,
        items: results.map((item) => ({
          '@type': 'FoodItem',
          ...item,
        })),
      });
    } catch (error: any) {
      res.status(500).json({
        success: false,
        message: error.message || 'Internal server error',
      });
    }
  }
}
