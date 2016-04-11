package com.apixandru.rummikub2;

import com.apixandru.rummikub.StateChangeListener;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 11, 2016
 */
public class TestRummikubImpl {

    @SuppressWarnings("unchecked")
    @Test
    public void testRegularRegister() {
        final Rummikub<Integer> rummikub = new RummikubImpl<>();
        rummikub.register("Dan", mock(StateChangeListener.class));
    }

}
