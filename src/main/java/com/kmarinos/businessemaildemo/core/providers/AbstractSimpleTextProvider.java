package com.kmarinos.businessemaildemo.core.providers;

import com.kmarinos.businessemaildemo.mail.business.DefaultMailContext;
import com.kmarinos.businessemaildemo.core.providers.internal.AbstractContextualSimpleTextProvider;

import java.util.function.Supplier;

public abstract class AbstractSimpleTextProvider<T> extends AbstractContextualSimpleTextProvider<T, DefaultMailContext> {
    public AbstractSimpleTextProvider(T boundedContext) {
        super(boundedContext);
    }

    public AbstractSimpleTextProvider(T boundedContext, String partSeparator) {
        super(boundedContext, partSeparator);
    }
    @Override
    public Supplier<DefaultMailContext> getTextContext() {
        return DefaultMailContext::new;
    }
}
