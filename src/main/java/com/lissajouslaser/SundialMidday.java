package com.lissajouslaser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Class to help find when midday would be if we used
 * true solar time.
 * Returns null if a city isn't in cityLongitudes.csv
 * NOTE - only works for cities that use geographical
 * UTC
 */
public class SundialMidday {
    private int dayOfYear;
    private Longitude longitude;

    /**
     * Returns String of time at which sun passes the meridian
     * at local time, i.e. midday true solar time.
     * Note it will mutate the Calender parameter.
     *
     * @param city
     * @param cal
     * @return
     */
    public String trueSolarTime(String city, Calendar cal) {

        try (Scanner scanner = new Scanner(new File("cityLongitudes.csv"))) {

            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                if (line[0].equals(city)) {
                    String[] longitudeTokens = line[1].split("°");
                    longitude = new Longitude(Double
                            .valueOf(longitudeTokens[0]), longitudeTokens[1]);
                    dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
                    break;
                }
            }
            if (longitude == null) {
                return null;
            }
            final int minuteToSeconds = 60;
            double eotWithLongitudeOffsetInSecs = (equationOfTime() + offset()) * minuteToSeconds;

            // set calendar to midday mean solar time
            final int midday = 12;
            cal.set(Calendar.HOUR_OF_DAY, midday);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            // adjust time to midday true solar time
            cal.add(Calendar.SECOND,
                    -(int) Math.round(eotWithLongitudeOffsetInSecs));

            // create formatted String
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date date = cal.getTime();
            String formattedTime = format.format(date);

            return formattedTime;

        } catch (IOException e) {
            System.out.println("A file error has occurred."
                    + " This program will now exit.");
            return null;
        }
    }

    /*
     * Returns the equation of time in minutes.
     */
    private double equationOfTime() {
        // Constants for the equation of time function.
        final double constA = 9.87;
        final int constB = 2;
        final double constC = 7.67;
        final double constD = 78.7;
        final int constE = 360;
        final int constF = 81;
        final int constG = 365;
        final double degreeToRad = Math.PI / 180;

        double theta = constE * (dayOfYear - constF) / constG;
        double waveFn1 = constA * Math.sin(constB * theta
                * degreeToRad);
        double waveFn2 = constC * Math.sin((theta + constD)
                * degreeToRad);
        return waveFn1 - waveFn2;
    }

    /*
     * Returns time offset in minutes of a city from the UTC
     * central meridian. A positve number means the real time
     * at the location is faster than at the UTC central
     * meridian.
     */
    private double offset() {
        final int longitudinalDegreesPerHour = 15;
        final double halfLongitudinalDegreesPerHour = 7.5;
        final int timeMinutesPerHour = 60;

        int utcOffset = (int) ((longitude.getDegrees()
                + halfLongitudinalDegreesPerHour)
                / longitudinalDegreesPerHour);
        if ("E".equals(longitude.getDirection())) {
            return (longitude.getDegrees()
                    - longitudinalDegreesPerHour * utcOffset)
                    * timeMinutesPerHour
                    / longitudinalDegreesPerHour;
        } else {
            return (longitudinalDegreesPerHour * utcOffset
                    - longitude.getDegrees())
                    * timeMinutesPerHour
                    / longitudinalDegreesPerHour;
        }
    }
}