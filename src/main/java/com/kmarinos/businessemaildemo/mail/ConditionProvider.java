package com.kmarinos.businessemaildemo.mail;

import java.util.Arrays;
import java.util.function.Predicate;

public interface ConditionProvider<T> {
    Predicate<T> getCondition();

    default Predicate<T> AND(Predicate<T>... predicates) {
        return Arrays.stream(predicates).reduce(c -> true, Predicate::and);
    }

    default Predicate<T> OR(Predicate<T>... predicates) {
        return Arrays.stream(predicates).reduce(c -> false, Predicate::or);
    }


}
