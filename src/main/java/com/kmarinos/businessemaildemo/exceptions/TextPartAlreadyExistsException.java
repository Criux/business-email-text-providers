package com.kmarinos.businessemaildemo.exceptions;

public class TextPartAlreadyExistsException extends RuntimeException {
    public TextPartAlreadyExistsException(String name) {
        super("TextPart with name %s already exists".formatted(name));
    }
}
