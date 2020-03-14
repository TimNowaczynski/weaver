package de.quarian.weaver.testing;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TimeStopUnitTest {

    @Test
    public void testTimeStop() {
        final TimeStop timeStop = TimeStop.acquire();
        final long start = timeStop.getPointInTime();

        assertThat(timeStop.secondsAgo(1).getPointInTime(), is(start - 1000L));
        assertThat(timeStop.minutesAgo(1).getPointInTime(), is(start - 1000L * 60L));
        assertThat(timeStop.hoursAgo(1).getPointInTime(), is(start - 1000L * 60L * 60L));
        assertThat(timeStop.daysAgo(1).getPointInTime(), is(start - 1000L * 60L * 60L * 24L));
        assertThat(timeStop.yearsAgo(1).getPointInTime(), is(start - 1000L * 60L * 60L * 24L * 365L));
    }

}
