package com.kmarinos.businessemaildemo.mail;

import com.kmarinos.businessemaildemo.DemoUtils;

import java.util.function.Supplier;

public interface StandardContextAwareTextProvider<T extends MailContext> extends ContextAwareTextProvider<T>{

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
