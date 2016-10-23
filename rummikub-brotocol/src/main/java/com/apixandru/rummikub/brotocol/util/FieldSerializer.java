package com.apixandru.rummikub.brotocol.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2016
 */
public interface FieldSerializer {

    void writeFields(final Object object, final DataOutput output) throws IOException;

    <T> T readFields(final Class<T> clasz, final DataInput input) throws IOException;

}
