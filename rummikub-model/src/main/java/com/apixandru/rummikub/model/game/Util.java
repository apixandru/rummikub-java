package com.apixandru.rummikub.model.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.reverse;
import static java.util.Collections.unmodifiableList;

/**
 * @author Alexandru-Constantin Bledea
 * @since November 22, 2015
 */
final class Util {

    private Util() {
    }

    @SafeVarargs
    static <E> List<List<E>> splitNonEmptyGroups(E... list) {
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

    static <E> List<E> revertedCopy(List<E> list) {
        final List<E> copy = new ArrayList<>(list);
        reverse(copy);
        return unmodifiableList(copy);
    }

    static <E> E[][] copyOf(final E[][] array) {
        final E[][] es = Arrays.copyOf(array, array.length);
        for (int i = 0; i < es.length; i++) {
            es[i] = Arrays.copyOf(es[i], es[i].length);
        }
        return es;
    }

}
