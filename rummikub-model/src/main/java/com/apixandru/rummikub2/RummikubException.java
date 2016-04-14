package com.apixandru.rummikub2;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class RummikubException extends RuntimeException {

    private final Reason reason;

    public RummikubException(final String message) {
        this(null, message);
    }

    public RummikubException(final Reason reason, final String message) {
        super(message);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }

    enum Reason {
        ONGOING_GAME
    }

}
