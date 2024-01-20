package com.kmarinos.businessemaildemo.exceptions;

public class NoFallbackTextProviderDefined extends RuntimeException{

    public NoFallbackTextProviderDefined(String boundedContextClass){
        super("No Fallback / Standard TextProvider defined in EmailTextProvider with context %s. Use EmailTextProvider#registerFallbackTextProvider to register one.".formatted(boundedContextClass));
    }
}
