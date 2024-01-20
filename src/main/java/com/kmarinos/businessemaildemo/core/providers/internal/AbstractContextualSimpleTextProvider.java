package com.kmarinos.businessemaildemo.core.providers.internal;

import com.kmarinos.businessemaildemo.mail.business.MailContext;
import com.kmarinos.businessemaildemo.core.model.TextPart;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.function.Function;

public abstract class AbstractContextualSimpleTextProvider<U, T extends MailContext> extends ContextualPartsTextProvider<U, T> {
    private final String PART_SEPARATOR;

    public AbstractContextualSimpleTextProvider(U boundedContext) {
        this(boundedContext, System.lineSeparator());
    }

    public AbstractContextualSimpleTextProvider(U boundedContext, String partSeparator) {
        super(boundedContext);
        this.PART_SEPARATOR = partSeparator;
    }

    public <R extends AbstractContextualSimpleTextProvider<U, T>> R addText(String text) {
        return addText(c -> text);
    }

    public <R extends AbstractContextualSimpleTextProvider<U, T>> R addText(Function<T, String> createText) {
        return this.addText(totalParts(), RandomStringUtils.random(10, true, false), createText);
    }

    public <R extends AbstractContextualSimpleTextProvider<U, T>> R addText(String name, Function<T, String> createText) {
        return this.addText(totalParts(), name, createText);
    }

    public <R extends AbstractContextualSimpleTextProvider<U, T>> R addText(int order, String name, Function<T, String> createText) {
        super.add(order, name, ctx -> new TextPart(totalParts() == 0 ? "" : PART_SEPARATOR, createText.apply(getContext().get()), ""));
        return (R) this;
    }
}
