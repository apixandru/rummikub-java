package com.apixandru.utils.fieldserializer;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.lang.reflect.Field;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2016
 */
public interface FieldSerializer {

    /**
     * @param field
     * @param object
     * @param output
     * @throws IllegalAccessException
     * @throws IOException
     */
    void deserialize(Field field, Object object, DataOutput output) throws IllegalAccessException, IOException;

    /**
     * @param field
     * @param object
     * @param input
     */
    void serialize(Field field, Object object, ObjectInput input) throws IOException, IllegalAccessException;

}
