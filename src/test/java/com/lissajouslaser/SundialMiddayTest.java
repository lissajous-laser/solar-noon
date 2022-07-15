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
    public void equationOfTimeWorksAtSunSlowest() {
        final double melbourne = 144.9631;
        // NOTE - date fields for Calendar class are ZERO index
        final int year = 2000;
        final int month = 1;
        final int date = 12;
        final int minEOT = -14;

        Longitude longitude = new Longitude(melbourne, "E");
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);
        SundialMidday sundialMidday = new SundialMidday(longitude, cal);
        assertTrue(sundialMidday.equationOfTime() < minEOT);
    }

    @Test
     public void equationOfTimeWorksAtSunFastest() {
        final double melbourne = 144.9631;
        // NOTE - date fields for Calendar class are ZERO index
        final int year = 2000;
        final int month = 10;
        final int date = 1;
        final int minEOT = 16;

        Longitude longitude = new Longitude(melbourne, "E");
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);
        SundialMidday sundialMidday = new SundialMidday(longitude, cal);
        assertTrue(sundialMidday.equationOfTime() > minEOT);
    }

    @Test
    public void testMelbourneWorks() {
        final double melbourne = 144.9631;
        // NOTE - date fields for Calendar class are ZERO index
        final int year = 2000;
        final int monthOf0714 = 6;
        final int dateOf0714 = 13;

        Longitude longitude = new Longitude(melbourne, "E");
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOf0714, dateOf0714);
        SundialMidday sundialMidday = new SundialMidday(longitude, cal);
        assertTrue(sundialMidday.trueSolarTime().contains("12:25"));
    }

    @Test
    public void testNewYorkWorks() {
        final double newYork = 74.0060;
        // NOTE - date fields for Calendar class are ZERO index
        final int year = 2000;
        final int monthOf0714 = 6;
        final int dateOf0714 = 13;

        Longitude longitude = new Longitude(newYork, "W");
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOf0714, dateOf0714);
        SundialMidday sundialMidday = new SundialMidday(longitude, cal);
        assertTrue(sundialMidday.trueSolarTime().contains("12:01"));
    }
}
