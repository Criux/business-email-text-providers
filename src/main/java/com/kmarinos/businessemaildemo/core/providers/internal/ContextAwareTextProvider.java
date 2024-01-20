package com.kmarinos.businessemaildemo.core.providers.internal;

import com.kmarinos.businessemaildemo.mail.business.MailContext;

import java.util.function.Supplier;

public interface ContextAwareTextProvider<T extends MailContext> {
    Supplier<T> getContext();

}
