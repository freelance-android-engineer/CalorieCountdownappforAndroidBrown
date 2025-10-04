import prisma from '@/config/prisma';
import { Request, Response } from 'express';
import { exerciseItemSchema } from '@/validations/exerciseItem.validation';

export class ExerciseItemController {
  async create(req: Request, res: Response): Promise<void> {
    try {
      const parsedData = exerciseItemSchema.parse(req.body);

      const exerciseItem = await prisma.exerciseItem.create({
        data: parsedData,
      });

      res.status(201).json({
        success: true,
        message: 'Exercise item created successfully',
        data: exerciseItem,
      });
    } catch (error: any) {
      if (error.name === 'ZodError') {
        res.status(400).json({
          success: false,
          message: 'Validation failed',
          errors: error.errors.map((e: any) => ({
            path: e.path.join('.'),
            message: e.message,
          })),
        });
        return;
      }
      res.status(500).json({ success: false, message: error.message || 'Internal server error' });
    }
  }

  async findAll(req: Request, res: Response): Promise<void> {
    try {
      const { $filter, $orderby, $top, $skip } = req.query;

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
        prisma.exerciseItem.findMany({ where, orderBy, skip, take }),
        prisma.exerciseItem.count({ where }),
      ]);

      res.json({
        success: true,
        count: data.length,
        total,
        data,
        message: 'Exercise items fetched successfully',
      });
    } catch (error: any) {
      res.status(500).json({ success: false, message: error.message || 'Internal server error' });
    }
  }

  async findOne(req: Request, res: Response): Promise<void> {
    try {
      const id = parseInt(req.params.id);
      const exerciseItem = await prisma.exerciseItem.findUnique({ where: { id } });
      if (!exerciseItem) {
        res.status(404).json({ success: false, message: 'Exercise item not found' });
        return;
      }
      res.json({
        success: true,
        message: 'Exercise item fetched successfully',
        data: exerciseItem,
      });
    } catch (error: any) {
      res.status(500).json({ success: false, message: error.message || 'Internal server error' });
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
        } else if (typeof value === 'number' || typeof value === 'boolean') {
          where[key] = value;
        }
      }

      const results = await prisma.exerciseItem.findMany({ where });

      res.json({
        '@context': context,
        '@type': 'SearchResultsPage',
        success: true,
        totalResults: results.length,
        items: results.map((item) => ({ '@type': 'ExerciseItem', ...item })),
      });
    } catch (error: any) {
      res.status(500).json({ success: false, message: error.message || 'Internal server error' });
    }
  }
}
