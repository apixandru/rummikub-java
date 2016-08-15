package com.apixandru.mocksocket;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 15, 2016
 */
@FunctionalInterface
interface DataReceiver<T> {

    T receive() throws IOException;

}
