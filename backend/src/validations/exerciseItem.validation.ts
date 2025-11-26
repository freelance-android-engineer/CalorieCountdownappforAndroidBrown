import { z } from 'zod';

export const exerciseItemSchema = z.object({
  exercise_name: z.string().optional(),
  energy_burnt_per_min: z.number().optional().default(10),
  client_weight: z.number().optional(),
  client_height_cm: z.number().optional(),
  cardio_or_strength: z.boolean().optional(),
  Unit_Weight_lbs: z.number().optional().default(100),
});

export type ExerciseItemInput = z.infer<typeof exerciseItemSchema>;
