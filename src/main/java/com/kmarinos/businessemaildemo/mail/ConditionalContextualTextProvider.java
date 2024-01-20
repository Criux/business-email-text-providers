package com.kmarinos.businessemaildemo.mail;

public interface ConditionalContextualTextProvider<T> extends ConditionalContextualRunner<T, StringBuilder, StringBuilder>, TextProvider {

    default StringBuilder build(StringBuilder stringBuilder) {
        return execute(stringBuilder);
    }
}
