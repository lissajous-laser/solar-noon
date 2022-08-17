package com.lissajouslaser;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.util.Calendar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the SundialMiddayTest.
 */
public class NoonCalculatorTest {

    @Test
    public void equationOfTimeWorksAtSunSlowest() {
        final double melbLongitude = 144.9631;
        // NOTE - date fields for Calendar class are ZERO index
        final int year = 2000;
        final int month = 1;
        final int date = 12;
        final double minEOT = -13.9;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Australia/Melbourne").getRules();

        NoonCalculator noonCalc = new NoonCalculator(melbLongitude, cal, zoneRules);
        assertTrue(noonCalc.equationOfTime() < minEOT);
    }

    @Test
     public void equationOfTimeWorksAtSunFastest() {
        final double melbLongitude = 144.9631;
        // NOTE - date fields for Calendar class are ZERO index
        final int year = 2000;
        final int month = 10;
        final int date = 1;
        final double maxEOT = 16.4;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Australia/Melbourne").getRules();

        NoonCalculator noonCalc = new NoonCalculator(melbLongitude, cal, zoneRules);
        assertTrue(noonCalc.equationOfTime() > maxEOT);
    }

    // - positive longitude
    // - daylight savings
    @Test
    public void melbourneLastMonthOfDstWorks() {
        final double melbLongitude = 144.9631;
        final int year = 2022;
        final int month = 2;
        final int date = 30;
        final int varianceSecs = 90;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Australia/Melbourne").getRules();

        final LocalTime expectedNoon = LocalTime.of(13, 24);

        NoonCalculator noonCalc = new NoonCalculator(melbLongitude, cal, zoneRules);
        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - positive longitude
    @Test
    public void melbourneFirstMonthOfNoDstWorks() {
        final double melbLongitude = 144.9631;
        final int year = 2022;
        final int month = 3;
        final int date = 5;
        final int varianceSecs = 90;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Australia/Melbourne").getRules();

        final LocalTime expectedNoon = LocalTime.of(12, 22);

        NoonCalculator noonCalc = new NoonCalculator(melbLongitude, cal, zoneRules);
        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - geographical UTC different to official UTC
    // - negative longitude
    // - daylight savings
    // - prime meridian is in sane time zone (geographic)
    @Test
    public void madridLastMonthOfDstWorks() {
        final double madridLongitude = -3.6834;
        final int year = 2021;
        final int month = 9;
        final int date = 25;
        final int varianceSecs = 90;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Europe/Madrid").getRules();

        final LocalTime expectedNoon = LocalTime.of(13, 58);

        NoonCalculator noonCalc = new NoonCalculator(madridLongitude, cal, zoneRules);
        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - geographical UTC different to official UTC
    // - negative longitude        final int varianceSecs = 90;c)
    @Test
    public void madridFirstMonthOfNoDstWorks() {
        final double madridLongitude = -3.6834;
        final int year = 2021;
        final int month = 10;
        final int date = 1;
        final int varianceSecs = 90;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Europe/Madrid").getRules();

        final LocalTime expectedNoon = LocalTime.of(12, 58);

        NoonCalculator noonCalc = new NoonCalculator(madridLongitude, cal, zoneRules);
        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - negative longitude
    // - daylight savings
    @Test
    public void yewYorkLastMonthOfDstWorks() {
        final double longitude = -73.9249;
        final int year = 2021;
        final int month = 9;
        final int date = 30;
        final int varianceSecs = 90;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("America/New_York").getRules();

        final LocalTime expectedNoon = LocalTime.of(12, 39);

        NoonCalculator noonCalc = new NoonCalculator(longitude, cal, zoneRules);
        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - negative longitude
    @Test
    public void newYorkFirstMonthOfNoDstWorks() {
        final double longitude = -73.9249;
        final int year = 2021;
        final int month = 10;
        final int date = 10;
        final int varianceSecs = 90;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("America/New_York").getRules();

        final LocalTime expectedNoon = LocalTime.of(11, 39);

        NoonCalculator noonCalc = new NoonCalculator(longitude, cal, zoneRules);
        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - geographical UTC different to official UTC
    // - positive longitude
    @Test
    public void istanbulWorks() {
        final double longitude = 29.01;
        final int year = 2021;
        final int month = 9;
        final int date = 1;
        final int varianceSecs = 90;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Asia/Istanbul").getRules();

        final LocalTime expectedNoon = LocalTime.of(12, 53);

        NoonCalculator noonCalc = new NoonCalculator(longitude, cal, zoneRules);
        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - geographical UTC different to official UTC
    // - negative longitude
    @Test
    public void buenosAiresWorks() {
        final double longitude = -58.3975;
        final int year = 2021;
        final int month = 9;
        final int date = 1;
        final int varianceSecs = 90;
        final LocalTime expectedNoon = LocalTime.of(12, 42);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("America/Argentina/Buenos_Aires").getRules();
        NoonCalculator noonCalc = new NoonCalculator(longitude, cal, zoneRules);

        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - positive longitude
    // - international dateline in same time zone
    @Test
    public void aucklandNoDstWorks() {
        final double longitude = 174.763;
        final int year = 2021;
        final int month = 9;
        final int date = 1;
        final int varianceSecs = 90;
        final LocalTime expectedNoon = LocalTime.of(13, 10);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Pacific/Auckland").getRules();
        NoonCalculator noonCalc = new NoonCalculator(longitude, cal, zoneRules);

        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }

    // - positive longitude
    // - official UTC is fractional hour
    // - international dateline in same time zone
    @Test
    public void kolkataWorks() {
        final double longitude = 88.3247;
        final int year = 2021;
        final int month = 9;
        final int date = 1;
        final int varianceSecs = 90;
        final LocalTime expectedNoon = LocalTime.of(11, 25);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        ZoneRules zoneRules = ZoneId.of("Asia/Kolkata").getRules();
        NoonCalculator noonCalc = new NoonCalculator(longitude, cal, zoneRules);

        assertTrue(
            Math.abs(noonCalc.trueSolarNoon().toSecondOfDay() - expectedNoon.toSecondOfDay())
            < varianceSecs
        );
    }
}
