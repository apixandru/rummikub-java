package com.apixandru.rummikub.connection;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
public class ConnectionLostException extends Exception {

    public ConnectionLostException() {
    }

    public ConnectionLostException(String message) {
        super(message);
    }

    public ConnectionLostException(String message, Throwable cause) {
        super(message, cause);
    }

}
