package com.corneille.product.exception;

public class AttributeAlreadyExistException extends RuntimeException {
    public AttributeAlreadyExistException(String message) {
        super(message);
    }
}
