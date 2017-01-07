package com.apixandru.rummikub.server;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class RummikubException extends RuntimeException {

    private final Reason reason;

    RummikubException(final Reason reason) {
        super(reason.message);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }

    public enum Reason {
        ONGOING_GAME("There is an ongoing game, try later."),
        NAME_TAKEN("Name already taken."),
        NO_NAME("Name is missing."),
        NO_LISTENER("Listener is missing.");

        private final String message;

        Reason(final String message) {
            this.message = message;
        }

    }

}
