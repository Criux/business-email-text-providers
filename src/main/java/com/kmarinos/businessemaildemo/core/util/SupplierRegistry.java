package com.kmarinos.businessemaildemo.core.util;

import com.kmarinos.businessemaildemo.core.EmailTextBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SupplierRegistry {

    private static Map<Class<?>, Supplier<?>> suppliers = new HashMap<>();
    private static Map<Class<?>, EmailTextBuilder<?>> emailTextProviderSuppliers = new HashMap<>();

    public static <T> Supplier<T> getRegisteredSupplier(Class<T> forClass) {
        return (Supplier<T>) suppliers.get(forClass);
    }
    public static <U> EmailTextBuilder<U> getEmailTextProvider(Class<U>conditionalContextClass){
        return (EmailTextBuilder<U>) emailTextProviderSuppliers.get(conditionalContextClass);
    }

    public static void registerSupplier(Class<?> forClass, Supplier<?> supplier) {
        suppliers.put(forClass, supplier);
    }

    public static <U>void registerEmailTextProvider(Class<?> conditionalContext, EmailTextBuilder<?> emailTextBuilder){emailTextProviderSuppliers.putIfAbsent(conditionalContext, emailTextBuilder);}
}
