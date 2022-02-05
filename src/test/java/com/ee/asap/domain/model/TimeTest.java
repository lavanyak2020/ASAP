package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.TimeUnit;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    @Nested
    class IsOlderThanOrSameTests {

        @Test
        void shouldReturnTrueIfTime1IsOldThanTime2() {
            Time time1 = new Time(2, TimeUnit.HOURS);
            Time time2 = new Time(2.5, TimeUnit.HOURS);

            assertTrue(time1.isOlderThanOrSame(time2));
        }

        @Test
        void shouldReturnTrueIfTime1IsSameTime2() {
            Time time1 = new Time(2, TimeUnit.HOURS);
            Time time2 = new Time(2, TimeUnit.HOURS);

            assertTrue(time1.isOlderThanOrSame(time2));
        }

        @Test
        void shouldReturnFalseIfTime1IsGreaterThanTime2() {
            Time time1 = new Time(2.75, TimeUnit.HOURS);
            Time time2 = new Time(2.5, TimeUnit.HOURS);

            assertFalse(time1.isOlderThanOrSame(time2));
        }

    }

    @Nested
    class AdditionTests {

        @Test
        void shouldAddTwoTimes() {
            Time oneHour = new Time(1, TimeUnit.HOURS);
            Time twoHours = new Time(2, TimeUnit.HOURS);
            Time threeHours = new Time(3, TimeUnit.HOURS);

            assertThat(oneHour.plus(twoHours), is(equalTo(threeHours)));
        }
    }
}