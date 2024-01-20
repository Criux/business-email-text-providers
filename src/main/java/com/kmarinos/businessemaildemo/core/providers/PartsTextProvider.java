package com.kmarinos.businessemaildemo.core.providers;

import com.kmarinos.businessemaildemo.mail.business.MailContext;
import com.kmarinos.businessemaildemo.core.model.TextPart;
import com.kmarinos.businessemaildemo.core.exceptions.TextPartAlreadyExistsException;
import com.kmarinos.businessemaildemo.core.providers.internal.StandardContextAwareTextProvider;
import com.kmarinos.businessemaildemo.core.providers.internal.TextProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class PartsTextProvider<T extends MailContext> implements StandardContextAwareTextProvider<T>, TextProvider {
    private final Map<String, Function<T, TextPart>> parts = new HashMap<>();
    private final List<PartSortingOrder> partSortingOrder = new ArrayList<>();


    public int totalParts() {
        return partSortingOrder.size();
    }

    protected void add(String name, Function<T, TextPart> createText) {
        add(totalParts(), name, createText);
    }

    protected void add(int order, String name, Function<T, TextPart> createText) {
        if (parts.get(name) != null) {
            throw new TextPartAlreadyExistsException(name);
        }
        parts.put(name, createText);

        if (partSortingOrder.isEmpty()) {
            partSortingOrder.add(new PartSortingOrder(name, order));
        } else {
            int posMin = 0;
            for (int i = 0; i < partSortingOrder.size(); i++) {
                int partVirtualPosition = partSortingOrder.get(i).getRequestedPosition();
                if (order > partVirtualPosition) {
                    posMin = partVirtualPosition;
                }
                if (order <= partVirtualPosition && order > posMin) {
                    partSortingOrder.add(i, new PartSortingOrder(name, order));
                    break;
                } else if (i == partSortingOrder.size() - 1) {
                    partSortingOrder.add(new PartSortingOrder(name, order));
                    break;
                }
            }
        }


    }

    @Override
    public StringBuilder build(StringBuilder stringBuilder) {

        partSortingOrder.stream()
                .map(o -> parts.get(o.getName()))
                .forEachOrdered(f -> f.apply(getContext().get()).build(stringBuilder));

        return stringBuilder;
    }

    @Override
    public String build() {
        return build(new StringBuilder()).toString();
    }

    @Data
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @AllArgsConstructor
    static class PartSortingOrder {
        @EqualsAndHashCode.Include
        String name;
        Integer requestedPosition;
    }
}
