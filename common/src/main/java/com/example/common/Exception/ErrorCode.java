package com.example.common.Exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_ORDER("ORDER_001", "Geçersiz sipariş verisi"),
    ORDER_NOT_FOUND("ORDER_002", "Sipariş bulunamadı"),
    INTERNAL_ERROR("GENERIC_001", "Beklenmeyen bir hata oluştu"),
    STOCK_NOT_FOUND("STOCK-001", "Stock item not found"),
    INSUFFICIENT_STOCK("STOCK-002", "Not enough stock available"),
    INVALID_REQUEST("STOCK-003", "Invalid stock request"),
    EMAIL_ALREADY_EXISTS("USER_001", "Email already exists"),
    USER_NOT_FOUND("USER_002", "User not found"),
    INVALID_CREDENTIALS("USER_003", "Invalid credentials");

    public final String code;
    public final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
