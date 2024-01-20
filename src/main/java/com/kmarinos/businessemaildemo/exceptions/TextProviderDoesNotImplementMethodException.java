package com.kmarinos.businessemaildemo.exceptions;

public class TextProviderDoesNotImplementMethodException extends RuntimeException{
    public TextProviderDoesNotImplementMethodException(String className,String callerClassName, String methodName) {
        super("The TextProvider of type %s cannot defer the method execution of %s() to %s, because it does not implement a method with that name.".formatted(callerClassName,methodName,className));
    }
}
