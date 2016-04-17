package com.apixandru.rummikub.server;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public final class Reference<T> implements Supplier<T> {

    private final AtomicReference<T> value = new AtomicReference<>();

    @Override
    public T get() {
        return this.value.get();
    }

    public void set(final T value) {
        this.value.set(value);
    }

}
