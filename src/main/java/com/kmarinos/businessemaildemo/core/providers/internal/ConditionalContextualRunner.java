package com.kmarinos.businessemaildemo.core.providers.internal;

public interface ConditionalContextualRunner<T, R, U> extends ConditionProvider<T> {

    T getConditionalContext();

    R execute(U executionContext);
}
