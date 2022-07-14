package com.lissajouslaser;

import java.util.Calendar;
import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the SundialMiddayTest.
 */
public class SundialMiddayTest {

    @Test
    public void testMelbourneWorks() {
        final double melbourne = 144.9631;
        Longitude longitude = new Longitude(melbourne, "E");
        Calendar cal = Calendar.getInstance();
        SundialMidday sundialMidday = new SundialMidday(longitude, cal);
        assertTrue(sundialMidday.trueSolarTime().contains("12:25"));
    }

    @Test
    public void testNewYorkWorks() {
        final double newYork = 74.0060;
        Longitude longitude = new Longitude(newYork, "W");
        Calendar cal = Calendar.getInstance();
        SundialMidday sundialMidday = new SundialMidday(longitude, cal);
        assertTrue(sundialMidday.trueSolarTime().contains("12:01"));
    }
}
