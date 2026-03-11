import { z } from 'zod';

export const geminiGenerateSchema = z.object({
  model: z.string().optional(),
  contents: z.string().transform((val) => JSON.parse(val)),
  temperature: z
    .string()
    .optional()
    .transform((val) => (val ? Number(val) : undefined)),
  maxOutputTokens: z
    .string()
    .optional()
    .transform((val) => (val ? Number(val) : undefined)),
});
