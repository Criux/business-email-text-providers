package com.kmarinos.businessemaildemo.mail;

import java.util.function.Supplier;

public interface ContextAwareTextProvider<T extends MailContext> {
    Supplier<T> getContext();

}
