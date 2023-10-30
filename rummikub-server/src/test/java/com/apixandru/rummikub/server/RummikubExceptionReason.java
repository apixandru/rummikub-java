package com.apixandru.rummikub.server;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 14, 2016
 */
class RummikubExceptionReason extends TypeSafeMatcher<RummikubException> {

    private final RummikubException.Reason reason;

    private RummikubExceptionReason(final RummikubException.Reason reason) {
        this.reason = reason;
    }

    static <T extends Throwable> Matcher<RummikubException> reason(final RummikubException.Reason reason) {
        return new RummikubExceptionReason(reason);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("reason ");
        description.appendValue(reason);
    }

    @Override
    protected boolean matchesSafely(RummikubException item) {
        return reason == item.getReason();
    }

    @Override
    protected void describeMismatchSafely(RummikubException item, Description description) {
        description.appendText(" expected instead of ").appendValue(item.getReason());
    }

}
