package com.ee.asap.domain;

import com.ee.asap.domain.model.Distance;
import com.ee.asap.domain.model.DistanceUnit;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class DistanceTest {

    @Nested
    class GreaterOrEqualsToTest {

        @Test
        void shouldReturnTrueIfDistance1IsGreaterThanDistance2() {
            Distance distance1 = new Distance(20f, DistanceUnit.KM);
            Distance distance2 = new Distance(10f, DistanceUnit.KM);

            assertTrue(distance1.isGreaterOrEqual(distance2));
        }

        @Test
        void shouldReturnTrueIfDistance1IsEqualToDistance2() {
            Distance distance1 = new Distance(20f, DistanceUnit.KM);
            Distance distance2 = new Distance(20f, DistanceUnit.KM);

            assertTrue(distance1.isGreaterOrEqual(distance2));
        }

        @Test
        void shouldReturnFalseIfDistance1IsNeitherGreaterNorEqualDistance2() {
            Distance distance1 = new Distance(10f, DistanceUnit.KM);
            Distance distance2 = new Distance(20f, DistanceUnit.KM);

            assertFalse(distance1.isGreaterOrEqual(distance2));
        }
    }

    @Nested
    class LessOrEqualsToTest {

        @Test
        void shouldReturnTrueIfDistance1IsLessThanDistance2() {
            Distance distance1 = new Distance(10f, DistanceUnit.KM);
            Distance distance2 = new Distance(20f, DistanceUnit.KM);

            assertTrue(distance1.isLessOrEqual(distance2));
        }

        @Test
        void shouldReturnTrueIfDistance1IsEqualToDistance2() {
            Distance distance1 = new Distance(10f, DistanceUnit.KM);
            Distance distance2 = new Distance(10f, DistanceUnit.KM);

            assertTrue(distance1.isLessOrEqual(distance2));
        }

        @Test
        void shouldReturnFalseIfDistance1IsNeitherLessNorEqualDistance2() {
            Distance distance1 = new Distance(20f, DistanceUnit.KM);
            Distance distance2 = new Distance(10f, DistanceUnit.KM);

            assertFalse(distance1.isLessOrEqual(distance2));
        }
    }

    @Test
    void shouldEquateTwoSameDistanceByValueAndUnit() {
        Distance distance1 = new Distance(10f, DistanceUnit.KM);
        Distance distance2 = new Distance(10f, DistanceUnit.KM);

        assertThat(distance1, is(equalTo(distance2)));
    }
}