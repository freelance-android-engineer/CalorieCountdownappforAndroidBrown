"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.GeminiController = void 0;
const genai_1 = require("@google/genai");
const gemini_validation_1 = require("@/validations/gemini.validation");
const ai = new genai_1.GoogleGenAI({
    apiKey: process.env.GOOGLE_API_KEY,
});
class GeminiController {
    generate(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const parsedData = gemini_validation_1.geminiGenerateSchema.parse(req.body);
                const { model = 'gemini-2.5-flash', contents, temperature = 0.4, maxOutputTokens = 2048, } = parsedData;
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
                const response = yield ai.models.generateContent({
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
            }
            catch (error) {
                if (error.name === 'ZodError') {
                    res.status(400).json({
                        success: false,
                        message: 'Validation failed',
                        errors: error.issues.map((e) => ({
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
        });
    }
}
exports.GeminiController = GeminiController;
