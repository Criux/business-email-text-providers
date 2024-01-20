package com.kmarinos.businessemaildemo.core.model;

import com.kmarinos.businessemaildemo.core.providers.internal.ContextualPartsTextProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StandByTextProviders {
    List<ContextualPartsTextProvider> textProviders = new ArrayList<>();
    int pos = -1;

    public Optional<ContextualPartsTextProvider> getNext() {
        pos++;
        if (textProviders.isEmpty() || pos >= textProviders.size()) {
            return Optional.empty();
        }

        return Optional.of(textProviders.get(pos));

    }

    public Optional<ContextualPartsTextProvider> getNext(String currentClassName) {
        try {
            var current = Class.forName(currentClassName);
            int currentIndex = -1;
            for (int i = 0; i < textProviders.size(); i++) {
                if (current.equals(textProviders.get(i).getClass())) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex == -1 || currentIndex == textProviders.size() - 1) {
                return Optional.empty();
            }
            return Optional.of(textProviders.get(currentIndex + 1));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    int getTotal() {
        return textProviders.size();
    }

    public void add(ContextualPartsTextProvider textProvider) {
        textProviders.add(textProvider);
    }
}
