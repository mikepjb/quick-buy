package org.example;

public class ProductApiException extends RuntimeException {
    private final String errorCode;

    public ProductApiException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
