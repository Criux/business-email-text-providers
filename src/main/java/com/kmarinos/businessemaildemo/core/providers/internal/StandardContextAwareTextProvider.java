package com.kmarinos.businessemaildemo.core.providers.internal;

import com.kmarinos.businessemaildemo.mail.demo.DemoUtils;
import com.kmarinos.businessemaildemo.mail.business.MailContext;

import java.util.function.Supplier;

public interface StandardContextAwareTextProvider<T extends MailContext> extends ContextAwareTextProvider<T> {

    Supplier<T>getTextContext();

    default Supplier<T> getContext() {
        return ()->{
            MailContext ctx = getTextContext().get();
            ctx.setLocale(DemoUtils.DEMO_LOCALE);
            ctx.setTenant(DemoUtils.DEMO_TENANT);
            return (T) ctx;
        };
    }
}
