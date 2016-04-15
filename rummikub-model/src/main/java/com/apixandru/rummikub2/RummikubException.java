package com.apixandru.rummikub2;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class RummikubException extends RuntimeException {

    private final Reason reason;

    RummikubException(final String message) {
        super(message);
        this.reason = null;
    }

    RummikubException(final Reason reason) {
        super(reason.message);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }

    enum Reason {
        ONGOING_GAME("There is an ongoing game, try later.");

        private final String message;

        Reason(final String message) {
            this.message = message;
        }

    }

}
