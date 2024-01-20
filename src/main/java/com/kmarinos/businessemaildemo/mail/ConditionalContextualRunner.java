package com.kmarinos.businessemaildemo.mail;

public interface ConditionalContextualRunner<T, R, U> extends ConditionProvider<T> {

    T getConditionalContext();

    R execute(U executionContext);
}
