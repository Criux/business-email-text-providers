package com.kmarinos.businessemaildemo;

import com.kmarinos.businessemaildemo.mail.EmailTextProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SupplierRegistry {

    private static Map<Class<?>, Supplier<?>> suppliers = new HashMap<>();
    private static Map<Class<?>, EmailTextProvider<?>> emailTextProviderSuppliers = new HashMap<>();

    public static <T> Supplier<T> getRegisteredSupplier(Class<T> forClass) {
        return (Supplier<T>) suppliers.get(forClass);
    }
    public static <U> EmailTextProvider<U> getEmailTextProvider(Class<U>conditionalContextClass){
        return (EmailTextProvider<U>) emailTextProviderSuppliers.get(conditionalContextClass);
    }

    public static void registerSupplier(Class<?> forClass, Supplier<?> supplier) {
        suppliers.put(forClass, supplier);
    }

    public static <U>void registerEmailTextProvider(Class<?> conditionalContext, EmailTextProvider<?> emailTextProvider){emailTextProviderSuppliers.putIfAbsent(conditionalContext, emailTextProvider);}
}
