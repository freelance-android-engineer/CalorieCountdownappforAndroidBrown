"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.statusCodes = exports.cookiesOption = exports.CONST_KEYS = void 0;
exports.CONST_KEYS = {
    ACCESS_TOKEN: 'accessToken',
    REFRESH_TOKEN: 'refreshToken',
};
const cookiesOption = (maxAge) => ({
    httpOnly: true,
    secure: process.env.NODE_ENV === 'production',
    sameSite: 'lax',
    maxAge: maxAge,
});
exports.cookiesOption = cookiesOption;
exports.statusCodes = {
    // General
    OK: 200,
    CREATED: 201,
    NO_CONTENT: 204,
    BAD_REQUEST: 400,
    UNAUTHORIZED: 401,
    FORBIDDEN: 403,
    NOT_FOUND: 404,
    CONFLICT: 409,
    INTERNAL_SERVER_ERROR: 500,
};
