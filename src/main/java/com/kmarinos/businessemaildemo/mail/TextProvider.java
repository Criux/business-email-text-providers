package com.kmarinos.businessemaildemo.mail;

public interface TextProvider {
    default String build() {
        return build(new StringBuilder()).toString();
    }

    StringBuilder build(StringBuilder stringBuilder);
}
