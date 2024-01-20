package com.kmarinos.businessemaildemo.core.providers.internal;

public interface TextProvider {
    default String build() {
        return build(new StringBuilder()).toString();
    }

    StringBuilder build(StringBuilder stringBuilder);
}
