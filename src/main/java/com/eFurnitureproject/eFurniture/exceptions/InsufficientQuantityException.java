package com.eFurnitureproject.eFurniture.exceptions;

public class InsufficientQuantityException extends RuntimeException{
    public InsufficientQuantityException(String message) {
        super(message);
    }
}
