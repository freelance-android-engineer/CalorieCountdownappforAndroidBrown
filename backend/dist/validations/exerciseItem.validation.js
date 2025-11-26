"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.exerciseItemSchema = void 0;
const zod_1 = require("zod");
exports.exerciseItemSchema = zod_1.z.object({
    exercise_name: zod_1.z.string().optional(),
    energy_burnt_per_min: zod_1.z.number().optional().default(10),
    client_weight: zod_1.z.number().optional(),
    client_height_cm: zod_1.z.number().optional(),
    cardio_or_strength: zod_1.z.boolean().optional(),
    Unit_Weight_lbs: zod_1.z.number().optional().default(100),
});
