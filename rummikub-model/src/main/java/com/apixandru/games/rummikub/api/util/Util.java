package com.apixandru.games.rummikub.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since November 22, 2015
 */
public final class Util {

    private Util() {
    }

    /**
     * @param <E>
     * @param list
     * @return
     */
    @SafeVarargs
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

    /**
     * @param list
     * @param <E>
     * @return
     */
    public static <E> List<E> revertedCopy(List<E> list) {
        if (null == list) {
            return Collections.emptyList();
        }
        final ArrayList<E> copy = new ArrayList<>(list);
        Collections.reverse(copy);
        return Collections.unmodifiableList(copy);
    }

    /**
     * @param array
     * @param <E>
     * @return
     */
    public static <E> E[][] copyOf(final E[][] array) {
        final E[][] es = Arrays.copyOf(array, array.length);
        for (int i = 0; i < es.length; i++) {
            es[i] = Arrays.copyOf(es[i], es[i].length);
        }
        return es;
    }

}
