"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.geminiGenerateSchema = void 0;
const zod_1 = require("zod");
exports.geminiGenerateSchema = zod_1.z.object({
    model: zod_1.z.string().optional(),
    contents: zod_1.z.string().transform((val) => JSON.parse(val)),
    temperature: zod_1.z
        .string()
        .optional()
        .transform((val) => (val ? Number(val) : undefined)),
    maxOutputTokens: zod_1.z
        .string()
        .optional()
        .transform((val) => (val ? Number(val) : undefined)),
});
