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
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.FoodItemController = void 0;
const prisma_1 = __importDefault(require("@/config/prisma"));
const foodItem_validation_1 = require("@/validations/foodItem.validation");
class FoodItemController {
    create(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const parsedData = foodItem_validation_1.foodItemSchema.parse(req.body);
                // Check for duplicate name
                const existingItem = yield prisma_1.default.foodItem.findFirst({
                    where: { food_item_name: { equals: parsedData.food_item_name, mode: 'insensitive' } },
                });
                if (existingItem) {
                    res.status(409).json({
                        success: false,
                        message: 'A food item with this name already exists',
                    });
                    return;
                }
                const foodItem = yield prisma_1.default.foodItem.create({
                    data: parsedData,
                });
                res.status(201).json({
                    success: true,
                    message: 'Food item created successfully',
                    data: foodItem,
                });
            }
            catch (error) {
                res.status(500).json({
                    success: false,
                    message: error.message || 'Internal server error',
                });
            }
        });
    }
    findAll(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const { $filter, $orderby, $top, $skip } = req.query;
                // Basic OData-like parsing
                let where = {};
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
                let orderBy = undefined;
                if ($orderby && typeof $orderby === 'string') {
                    const [field, direction] = $orderby.split(' ');
                    orderBy = { [field]: (direction === null || direction === void 0 ? void 0 : direction.toLowerCase()) === 'desc' ? 'desc' : 'asc' };
                }
                const take = $top ? parseInt($top, 10) : 20;
                const skip = $skip ? parseInt($skip, 10) : 0;
                const [data, total] = yield Promise.all([
                    prisma_1.default.foodItem.findMany({ where, orderBy, skip, take }),
                    prisma_1.default.foodItem.count({ where }),
                ]);
                res.json({
                    success: true,
                    message: 'Food items fetched successfully',
                    count: data.length,
                    total,
                    data,
                });
            }
            catch (error) {
                res.status(500).json({
                    success: false,
                    message: error.message || 'Internal server error',
                });
            }
        });
    }
    findOne(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const id = parseInt(req.params.id);
                const foodItem = yield prisma_1.default.foodItem.findUnique({ where: { id } });
                if (!foodItem) {
                    res.status(404).json({ success: false, message: 'Food item not found' });
                    return;
                }
                res.json({ success: true, message: 'Food item fetched successfully', data: foodItem });
            }
            catch (error) {
                res.status(500).json({
                    success: false,
                    message: error.message || 'Internal server error',
                });
            }
        });
    }
    search(req, res) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const { '@context': context, '@type': type, query } = req.body;
                if (!context || !type || !query || typeof query !== 'object') {
                    res.status(400).json({
                        success: false,
                        message: 'Invalid JSON-LD format. Must include @context, @type, and query object.',
                    });
                    return;
                }
                const where = {};
                for (const [key, value] of Object.entries(query)) {
                    if (typeof value === 'string') {
                        where[key] = { contains: value, mode: 'insensitive' };
                    }
                    else if (typeof value === 'number') {
                        where[key] = value;
                    }
                }
                const results = yield prisma_1.default.foodItem.findMany({ where });
                res.json({
                    '@context': context,
                    '@type': 'SearchResultsPage',
                    success: true,
                    totalResults: results.length,
                    items: results.map((item) => (Object.assign({ '@type': 'FoodItem' }, item))),
                });
            }
            catch (error) {
                res.status(500).json({
                    success: false,
                    message: error.message || 'Internal server error',
                });
            }
        });
    }
}
exports.FoodItemController = FoodItemController;
