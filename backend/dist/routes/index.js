"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const foodItem_routes_1 = __importDefault(require("@/routes/foodItem.routes"));
const exerciseItem_routes_1 = __importDefault(require("@/routes/exerciseItem.routes"));
const gemini_routes_1 = __importDefault(require("@/routes/gemini.routes"));
const router = (0, express_1.Router)();
router.use('/food', foodItem_routes_1.default);
router.use('/exercise', exerciseItem_routes_1.default);
router.use('/gemini', gemini_routes_1.default);
exports.default = router;
