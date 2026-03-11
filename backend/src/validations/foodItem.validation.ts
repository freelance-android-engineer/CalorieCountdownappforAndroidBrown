import { z } from 'zod';

export const foodItemSchema = z.object({
  food_type: z.string().max(100).optional(),
  food_item_name: z.string().max(100).optional(),
  grams_per_serving: z.number().optional(),
  calories_per_100g: z.number().optional(),
  fat_per_100g: z.number().optional(),
  saturated_fat: z.number().optional(),
  trans_fat: z.number().optional(),
  protein_per_100g: z.number().optional(),
  carbs_per_100g: z.number().optional(),
  sugar_per_100g: z.number().optional(),
  salt_per_100g: z.number().optional(),
  wellbeing_index: z.number().optional(),
  fiber: z.number().optional(),
  price_sterling: z.number().optional(),
  category: z.string().max(100).optional(),
  polyunsaturated: z.number().optional(),
  monounsaturated: z.number().optional(),
  cholesterol_mg: z.number().optional(),
  sodium_mg: z.number().optional(),
  potassium_mg: z.number().optional(),
  vitamin_a_percent: z.number().optional(),
  vitamin_c_percent: z.number().optional(),
  calcium_percent: z.number().optional(),
  iron_percent: z.number().optional(),
});

export type FoodItemInput = z.infer<typeof foodItemSchema>;
