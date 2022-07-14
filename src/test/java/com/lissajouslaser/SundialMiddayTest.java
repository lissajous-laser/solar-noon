package com.lissajouslaser;

import java.util.Calendar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the SundialMiddayTest.
 */
public class SundialMiddayTest {

    @Test
    public void testMelbourneWorks() {
        SundialMidday sundialMidday = new SundialMidday();
        Calendar cal = Calendar.getInstance();
        assertTrue(sundialMidday.trueSolarTime("Melbourne", cal).contains("12:25"));
    }

    @Test
    public void testLondonWorks() {
        SundialMidday sundialMidday = new SundialMidday();
        Calendar cal = Calendar.getInstance();
        assertTrue(sundialMidday.trueSolarTime("London", cal).contains("12:05")
                || sundialMidday.trueSolarTime("London", cal).contains("12:06"));
    }

    @Test
    public void testNewYorkWorks() {
        SundialMidday sundialMidday = new SundialMidday();
        Calendar cal = Calendar.getInstance();
        assertTrue(sundialMidday.trueSolarTime("New York", cal).contains("12:01"));
    }

    @Test
    public void enteringUnlistedCityReturnsNull() {
        SundialMidday sundialMidday = new SundialMidday();
        Calendar cal = Calendar.getInstance();
        assertEquals(null, sundialMidday.trueSolarTime("Calcutta", cal));
    }
}
