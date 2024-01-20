package com.kmarinos.businessemaildemo.core.model;

public record TextPart(String prefix, String text, String suffix) {

    public void build(StringBuilder stringBuilder) {
        if (text == null) {
            return;
        }
        if (prefix != null) {
            stringBuilder.append(prefix);
        }
        stringBuilder.append(text);
        if (suffix != null) {
            stringBuilder.append(suffix);
        }
    }
}
