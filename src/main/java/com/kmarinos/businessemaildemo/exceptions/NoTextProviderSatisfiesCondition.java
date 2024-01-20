package com.kmarinos.businessemaildemo.exceptions;

public class NoTextProviderSatisfiesCondition extends RuntimeException {

    public NoTextProviderSatisfiesCondition(Class<?> contextClass) {
        super("The state of context of type %s is not covered by any registered TextProviders".formatted(contextClass));
    }
}
