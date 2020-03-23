package de.quarian.weaver.testing;

import static de.quarian.weaver.util.TimeConstants.ONE_SECOND;
import static de.quarian.weaver.util.TimeConstants.ONE_MINUTE;
import static de.quarian.weaver.util.TimeConstants.ONE_HOUR;
import static de.quarian.weaver.util.TimeConstants.ONE_DAY;
import static de.quarian.weaver.util.TimeConstants.ONE_YEAR;

public class TimeStop {

    private final long pointInTime;

    private TimeStop(final long pointInTime) {
        this.pointInTime = pointInTime;
    }

    public static TimeStop acquire() {
        return new TimeStop(System.currentTimeMillis());
    }

    public TimeStop secondsAgo(final long seconds) {
        return new TimeStop(pointInTime - (seconds * ONE_SECOND));
    }
    public TimeStop minutesAgo(final long minutes) {
        return new TimeStop(pointInTime - (minutes * ONE_MINUTE));
    }

    public TimeStop hoursAgo(final long hours) {
        return new TimeStop(pointInTime - (hours * ONE_HOUR));
    }

    public TimeStop daysAgo(final long days) {
        return new TimeStop(pointInTime - (days * ONE_DAY));
    }

    public TimeStop yearsAgo(final long year) {
        return new TimeStop(pointInTime - (year * ONE_YEAR));
    }

    public long getPointInTime() {
        return pointInTime;
    }
}
