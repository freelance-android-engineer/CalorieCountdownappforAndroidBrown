import { Request, Response } from 'express';
import { GoogleGenAI } from '@google/genai';
import { geminiGenerateSchema } from '@/validations/gemini.validation';

const ai = new GoogleGenAI({
  apiKey: process.env.GOOGLE_API_KEY,
});

export class GeminiController {
  async generate(req: Request, res: Response): Promise<void> {
    try {
      const parsedData = geminiGenerateSchema.parse(req.body);

      const {
        model = 'gemini-2.5-flash',
        contents,
        temperature = 0.4,
        maxOutputTokens = 2048,
      } = parsedData;

      // If image uploaded via multipart
      if (req.file) {
        const base64Image = req.file.buffer.toString('base64');

        contents[contents.length - 1].parts.push({
          inlineData: {
            mimeType: req.file.mimetype,
            data: base64Image,
          },
        });
      }

      const response = await ai.models.generateContent({
        model,
        contents,
        config: {
          temperature,
          maxOutputTokens,
        },
      });

      res.status(200).json({
        success: true,
        message: 'Gemini response generated successfully',
        data: {
          text: response.text,
          raw: response,
        },
      });
    } catch (error: any) {
      if (error.name === 'ZodError') {
        res.status(400).json({
          success: false,
          message: 'Validation failed',
          errors: error.issues.map((e: any) => ({
            path: e.path.join('.'),
            message: e.message,
          })),
        });
        return;
      }

      res.status(500).json({
        success: false,
        message: error.message || 'Internal server error',
      });
    }
  }
}
