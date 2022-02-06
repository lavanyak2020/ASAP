package com.ee.asap.domain.model;

import com.ee.asap.domain.constants.enums.WeightUnit;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeightTest {

    @Nested
    class GreaterOrEqualsToTest {

        @Test
        void shouldReturnTrueIfWeight1IsGreaterThanWeight2() {
            Weight weight1 = new Weight(20f, WeightUnit.KG);
            Weight weight2 = new Weight(10f, WeightUnit.KG);

            assertTrue(weight1.isGreaterOrEqual(weight2));
        }

        @Test
        void shouldReturnTrueIfWeight1IsEqualToWeight2() {
            Weight weight1 = new Weight(20f, WeightUnit.KG);
            Weight weight2 = new Weight(20f, WeightUnit.KG);

            assertTrue(weight1.isGreaterOrEqual(weight2));
        }

        @Test
        void shouldReturnFalseIfWeight1IsNeitherGreaterNorEqualWeight2() {
            Weight weight1 = new Weight(10f, WeightUnit.KG);
            Weight weight2 = new Weight(20f, WeightUnit.KG);

            assertFalse(weight1.isGreaterOrEqual(weight2));
        }
    }

    @Nested
    class LessOrEqualsToTest {

        @Test
        void shouldReturnTrueIfWeight1IsLessThanWeight2() {
            Weight weight1 = new Weight(10f, WeightUnit.KG);
            Weight weight2 = new Weight(20f, WeightUnit.KG);

            assertTrue(weight1.isLessOrEqual(weight2));
        }

        @Test
        void shouldReturnTrueIfWeight1IsEqualToWeight2() {
            Weight weight1 = new Weight(10f, WeightUnit.KG);
            Weight weight2 = new Weight(10f, WeightUnit.KG);

            assertTrue(weight1.isLessOrEqual(weight2));
        }

        @Test
        void shouldReturnFalseIfWeight1IsNeitherLessNorEqualWeight2() {
            Weight weight1 = new Weight(20f, WeightUnit.KG);
            Weight weight2 = new Weight(10f, WeightUnit.KG);

            assertFalse(weight1.isLessOrEqual(weight2));
        }
    }

    @Nested
    class GreaterThanTest {

        @Test
        void shouldReturnTrueIfWeight1IsGreaterThanWeight2() {
            Weight weight1 = new Weight(20f, WeightUnit.KG);
            Weight weight2 = new Weight(10f, WeightUnit.KG);

            assertTrue(weight1.isGreaterThan(weight2));
        }

        @Test
        void shouldReturnFalseIfWeight1IsEqualToWeight2() {
            Weight weight1 = new Weight(10f, WeightUnit.KG);
            Weight weight2 = new Weight(10f, WeightUnit.KG);

            assertFalse(weight1.isGreaterThan(weight2));
        }

        @Test
        void shouldReturnFalseIfWeight1IsLessThanWeight2() {
            Weight weight1 = new Weight(10f, WeightUnit.KG);
            Weight weight2 = new Weight(20f, WeightUnit.KG);

            assertFalse(weight1.isGreaterThan(weight2));
        }
    }

    @Test
    void shouldEquateTwoSameWeightByValueAndUnit() {
        Weight weight1 = new Weight(10, WeightUnit.KG);
        Weight weight2 = new Weight(10, WeightUnit.KG);

        assertThat(weight1, is(equalTo(weight2)));
    }
}