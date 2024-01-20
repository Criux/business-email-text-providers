package com.kmarinos.businessemaildemo.exceptions;

public class NoDeferredTextProviderException extends RuntimeException {
    public NoDeferredTextProviderException(String className, String methodName) {
        super("No TextProvider could be deferred for %s while calling %s()".formatted(className, methodName));
    }
}
