"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const Gemini_controller_1 = require("@/controllers/Gemini.controller");
const express_1 = require("express");
const multer_1 = __importDefault(require("multer"));
const router = (0, express_1.Router)();
const controller = new Gemini_controller_1.GeminiController();
const upload = (0, multer_1.default)({
    storage: multer_1.default.memoryStorage(),
});
router.post('/generate', upload.single('image'), controller.generate.bind(controller));
exports.default = router;
