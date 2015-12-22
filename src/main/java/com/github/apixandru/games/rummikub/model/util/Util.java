package com.github.apixandru.games.rummikub.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Alexandru-Constantin Bledea
 * @since November 22, 2015
 */
public final class Util {

    private Util() {
    }

    private static Predicate notNull = o -> null != o;

    public static <T> Predicate<T> notNull() {
        return notNull;
    }

    /**
     * @param <E>
     * @param list
     * @return
     */
    public static <E> List<List<E>> splitNonEmptyGroups(E... list) {
        List<List<E>> result = new ArrayList<>();
        List<E> elementList = new ArrayList<>();
        for (E element : Arrays.asList(list)) {
            if (null != element) {
                elementList.add(element);
                continue;
            }
            if (elementList.isEmpty()) {
                continue;
            }
            result.add(elementList);
            elementList = new ArrayList<>();
        }
        if (!elementList.isEmpty()) {
            result.add(elementList);
        }
        return result;
    }

}
