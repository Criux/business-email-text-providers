package com.kmarinos.businessemaildemo.core;

import com.kmarinos.businessemaildemo.core.model.StandByTextProviders;
import com.kmarinos.businessemaildemo.core.util.SupplierRegistry;
import com.kmarinos.businessemaildemo.mail.business.MailContext;
import com.kmarinos.businessemaildemo.core.exceptions.NoFallbackTextProviderDefined;
import com.kmarinos.businessemaildemo.core.exceptions.NoTextProviderSatisfiesCondition;
import com.kmarinos.businessemaildemo.core.providers.internal.ContextualPartsTextProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EmailTextBuilder<T> {
    Class<T> boundedContext;
    List<TextProviderClassesSortingOrder<Supplier<? extends ContextualPartsTextProvider<T, ? extends MailContext>>>> textProviderSuppliers = new ArrayList<>();
    Supplier<? extends ContextualPartsTextProvider<T, ? extends MailContext>> fallbackTextProviderSupplier;

    public ContextualPartsTextProvider getFallbackTextProvider(){
        if(fallbackTextProviderSupplier==null){
            throw new NoFallbackTextProviderDefined(boundedContext.getName());
        }
        return fallbackTextProviderSupplier.get();
    }

    @Getter
    static ThreadLocal<StandByTextProviders> standByTextProviders = new ThreadLocal<>();

    public String getText() {
        standByTextProviders.set(new StandByTextProviders());
        Class boundedContext = null;
        var currentTextProviderSuppliers = new ArrayList<>(textProviderSuppliers);
        if (fallbackTextProviderSupplier != null) {
            putInCorrectPosition(currentTextProviderSuppliers, Integer.MAX_VALUE, new TextProviderClassesSortingOrder<>(fallbackTextProviderSupplier, Integer.MAX_VALUE));
        }
        for (TextProviderClassesSortingOrder<Supplier<? extends ContextualPartsTextProvider<T, ? extends MailContext>>> textProviderInstanceSupplier : currentTextProviderSuppliers) {
            var textProvider = textProviderInstanceSupplier.getElement().get();
            boundedContext = textProvider.getConditionalContext().getClass();
            if (textProvider.getCondition().test(textProvider.getConditionalContext())) {
                standByTextProviders.get().add(textProvider);
            }
        }
        Class finalBoundedContext = boundedContext;
        return standByTextProviders.get().getNext().orElseThrow(() -> new NoTextProviderSatisfiesCondition(finalBoundedContext)).execute(new StringBuilder()).toString();

    }

    public EmailTextBuilder<T> registerTextProvider(int sortingPosition, Supplier<? extends ContextualPartsTextProvider<T, ? extends MailContext>> textProviderSupplier) {
        boundedContext= (Class<T>) textProviderSupplier.get().getConditionalContext().getClass();
        SupplierRegistry.registerEmailTextProvider(boundedContext,this);
        SupplierRegistry.registerSupplier(textProviderSupplier.get().getClass(), textProviderSupplier);
        putInCorrectPosition(textProviderSuppliers, sortingPosition, new TextProviderClassesSortingOrder<>(textProviderSupplier, sortingPosition));
        return this;
    }

    public void registerFallbackTextProvider(Supplier<? extends ContextualPartsTextProvider<T, ? extends MailContext>> textProviderSupplier) {
        boundedContext= (Class<T>) textProviderSupplier.get().getConditionalContext().getClass();
        SupplierRegistry.registerEmailTextProvider(boundedContext,this);
        SupplierRegistry.registerSupplier(textProviderSupplier.get().getClass(), textProviderSupplier);
        if (!textProviderSupplier.get().getCondition().test(null)) {
            //log.warn("Fallback TextProvider's condition needs to always evaluate to true");
        }
        fallbackTextProviderSupplier = textProviderSupplier;
    }

    @Data
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @AllArgsConstructor
    static class TextProviderClassesSortingOrder<U> {
        @EqualsAndHashCode.Include
        U element;
        Integer requestedPosition;
    }

    private void putInCorrectPosition(List<TextProviderClassesSortingOrder<Supplier<? extends ContextualPartsTextProvider<T, ? extends MailContext>>>> list, int order, TextProviderClassesSortingOrder<Supplier<? extends ContextualPartsTextProvider<T, ? extends MailContext>>> element) {
        if (list.isEmpty()) {
            list.add(element);
        } else {
            int posMin = 0;
            for (int i = 0; i < list.size(); i++) {
                int partVirtualPosition = list.get(i).getRequestedPosition();
                if (order > partVirtualPosition) {
                    posMin = partVirtualPosition;
                }
                if (order <= partVirtualPosition && order > posMin) {
                    list.add(i, element);
                    break;
                } else if (i == list.size() - 1) {
                    list.add(element);
                    break;
                }
            }
        }
    }
}
