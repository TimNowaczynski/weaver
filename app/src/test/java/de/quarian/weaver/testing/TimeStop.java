package de.quarian.weaver.testing;

public class TimeStop {

    private static final long ONE_SECOND = 1000L;
    private static final long ONE_MINUTE = ONE_SECOND * 60L;
    private static final long ONE_HOUR = ONE_MINUTE * 60L;
    private static final long ONE_DAY = ONE_HOUR * 24L;
    private static final long ONE_YEAR = ONE_DAY * 365L;

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
