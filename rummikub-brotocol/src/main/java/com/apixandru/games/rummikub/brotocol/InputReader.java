package com.apixandru.games.rummikub.brotocol;

import java.io.DataInput;
import java.io.IOException;

/**
 * @param <T>
 * @author Alexandru-Constantin Bledea
 * @since 1/14/16
 */
interface InputReader<T> {

    /**
     * @param input
     * @return
     * @throws IOException
     */
    T read(DataInput input) throws IOException;

}
