package com.apixandru.utils.fieldserializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2016
 */
public interface FieldSerializer {

    /**
     * @param object the object
     * @param output the output
     * @throws IOException
     */
    void writeFields(final Object object, final DataOutput output) throws IOException;

    /**
     * @param clasz the class to deserialize
     * @param input the input
     * @param <T>   the type
     * @return the deserialized object
     * @throws IOException
     */
    <T> T readFields(final Class<T> clasz, final DataInput input) throws IOException;

}
