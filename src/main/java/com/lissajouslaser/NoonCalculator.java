package com.lissajouslaser;

// import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.zone.ZoneRules;
import java.util.Calendar;
// import java.util.Date;

/**
 * Class to help find solar noon.
 */
public class NoonCalculator {
    private final int longitudeDegreesPerHour = 15;
    private final int secondsPerHour = 3600;
    private final int secondsPerMinute = 60;

    private double longitude;
    private Calendar cal;
    private ZoneRules zoneRules;

    /**
     * Constructor.
     */
    public NoonCalculator(double longitude, Calendar cal, ZoneRules rules) {
        this.cal = cal;
        this.longitude = longitude;
        this.zoneRules = rules;
    }

    /**
     * Returns a LocalTime of the time at which sun passes
     * the meridian at local time, with daylight savings
     * adjusted if required.
     */
    public LocalTime trueSolarNoon() {

        // Set calendar to midday mean solar time,
        // note that Calendar is zero index.
        final int midday = 12;
        int daylightSavingOffsetSecs = 0;

        if (zoneRules.isDaylightSavings(cal.toInstant())) {
            daylightSavingOffsetSecs += dayLightSavingsOffsetSecs();
        }

        LocalTime noon = LocalTime.of(midday, 0);

        // Adjust time to midday true solar time;
        // plusSeconds() returns a new LocalTime
        noon = noon.plusSeconds(
                timeZoneOffsetSecs()
                + daylightSavingOffsetSecs
                - longitudinalOffset()
                - (int) (equationOfTime() * secondsPerMinute)
        );
        return noon;
    }

    /**
     * Returns the equation of time in minutes.
     */
    public double equationOfTime() {
        // Constants for the equation of time function.
        final double constA = 9.87;
        final int constB = 2;
        final double constC = 7.67;
        final double constD = 78.7;
        final int constE = 360;
        final int constF = 81;
        final int constG = 365;
        final double degreeToRad = Math.PI / 180;

        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);

        double theta = constE * (dayOfYear - constF) / constG;
        double waveFn1 = constA * Math.sin(constB * theta
                * degreeToRad);
        double waveFn2 = constC * Math.sin((theta + constD)
                * degreeToRad);
        return waveFn1 - waveFn2;
    }

    /**
     * Returns time offset in seonds of a city from the central
     * meridian of its time zone.
     * A positve number means the real time at the location is
     * faster than its central meridian.
     */
    private int longitudinalOffset() {

        return (int) ((longitude - longitudeDegreesPerHour * geographicUtc())
                * secondsPerHour
                / longitudeDegreesPerHour);
    }

    /**
     * Where UFC offset of the longitude if based purely on
     * geographic location, and not government mandate.
     */
    private int geographicUtc() {
        int geographicUtc;

        if (longitude > 0) {
            geographicUtc = (int) ((longitude + longitudeDegreesPerHour / 2)
                    / longitudeDegreesPerHour);
        } else {
            geographicUtc = (int) ((longitude - longitudeDegreesPerHour / 2)
                    / longitudeDegreesPerHour);
        }
        return geographicUtc;
    }

    /**
     * Gives standard offset of a ZoneRules in hours, ie.
     * before any daylight savings offset.
     */
    private float officialUtc() {
        float utc = (float) zoneRules
                .getStandardOffset(cal.toInstant())
                .getTotalSeconds()
                / secondsPerHour;
        return utc;
    }

    /**
     * Gives the offset of a location's official UTC
     * from the geographical UTC in seconds.
     * NOTE - this is not the offset of a time zone from
     * the prime meridian.
     */
    private int timeZoneOffsetSecs() {
        return (int) ((officialUtc() - geographicUtc()) * secondsPerHour);
    }

    /**
     * Gives daylight savings offset of a ZoneRules in
     * seconds.
     */
    private int dayLightSavingsOffsetSecs() {
        int daylightSavingsHours = (int) zoneRules
                .getDaylightSavings(cal.toInstant())
                .getSeconds();
        return daylightSavingsHours;
    }
}
