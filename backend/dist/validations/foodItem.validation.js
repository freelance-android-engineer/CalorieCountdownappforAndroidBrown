"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.foodItemSchema = void 0;
const zod_1 = require("zod");
exports.foodItemSchema = zod_1.z.object({
    food_type: zod_1.z.string().max(100).optional(),
    food_item_name: zod_1.z.string().max(100).optional(),
    grams_per_serving: zod_1.z.number().optional(),
    calories_per_100g: zod_1.z.number().optional(),
    fat_per_100g: zod_1.z.number().optional(),
    saturated_fat: zod_1.z.number().optional(),
    trans_fat: zod_1.z.number().optional(),
    protein_per_100g: zod_1.z.number().optional(),
    carbs_per_100g: zod_1.z.number().optional(),
    sugar_per_100g: zod_1.z.number().optional(),
    salt_per_100g: zod_1.z.number().optional(),
    wellbeing_index: zod_1.z.number().optional(),
    fiber: zod_1.z.number().optional(),
    price_sterling: zod_1.z.number().optional(),
    category: zod_1.z.string().max(100).optional(),
    polyunsaturated: zod_1.z.number().optional(),
    monounsaturated: zod_1.z.number().optional(),
    cholesterol_mg: zod_1.z.number().optional(),
    sodium_mg: zod_1.z.number().optional(),
    potassium_mg: zod_1.z.number().optional(),
    vitamin_a_percent: zod_1.z.number().optional(),
    vitamin_c_percent: zod_1.z.number().optional(),
    calcium_percent: zod_1.z.number().optional(),
    iron_percent: zod_1.z.number().optional(),
});
