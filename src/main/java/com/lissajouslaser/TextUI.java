package com.lissajouslaser;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for user interface.
 */
public class TextUI {
    private Map<String, Double> citiesAndLongitudes;
    private Scanner scanner;

    /**
     * Constructor.
     */
    public TextUI() {
        citiesAndLongitudes = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    /**
     * Constructor.
     */
    public TextUI(Scanner scanner) {
        citiesAndLongitudes = new HashMap<>();
        this.scanner = scanner;
    }

    /**
     * Main public method for creating the user interface.
     */
    public LocalTime start() {

        Calendar cal = Calendar.getInstance();
        Object longitudeOrNull; // Wrapper for value obtained from citiesAndLongitudes
        double longitude;
        String input;
        ZoneRules zoneRules = null;

        loadCityDataFile();

        System.out.println("\nThis program will give you the time of midday at "
                + "true solar time");
        while (true) {

            System.out.println("\nEnter in a city:");
            input = scanner.nextLine().trim().toLowerCase();

            longitudeOrNull = citiesAndLongitudes.get(input);
            // Make sure key value pair exists in citiesAndLongitudes.
            if (longitudeOrNull == null) {
                System.out.println("Sorry there is no matching city. Try again.");
                continue;
            }
            longitude = (double) longitudeOrNull;

            // Find matches in zone IDs from java.time.ZoneID
            for (String zoneID : ZoneId.getAvailableZoneIds()) {

                // If zone has a region qualified name, remove
                // region qualifier.
                String[] qualifiedZoneName = zoneID.split("/");
                String zoneName = qualifiedZoneName[qualifiedZoneName.length - 1]
                        .replace('_', ' ')
                        .toLowerCase();

                if (zoneName.equals(input.toLowerCase())) {
                    zoneRules = ZoneId.of(zoneID).getRules();
                }
            }
            if (zoneRules == null) {
                System.out.println("Sorry there is no matching city. Try again.");
                continue;
            }
            break;
        }

        System.out.println("\nEnter a date (in DDMM format) "
                + "you would like to check. Leave blank for today.");

        while (true) {
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                break;
            }
            if (input.matches("\\d{4}")) {
                final int monthIndex = 2;
                final int zeroIdxOffset = -1;

                cal.set(cal.get(Calendar.YEAR),                           // current year
                        removePaddedZeros(input.substring(monthIndex))    // set month
                                + zeroIdxOffset,
                        removePaddedZeros(input.substring(0, monthIndex)) // set day
                                + zeroIdxOffset);
                break;
            }
            System.out.println("Sorry this is not a valid date. Try again.");
        }

        NoonCalculator sundialMidday = new NoonCalculator(longitude, cal, zoneRules);
        LocalTime middayTrueSolarTime = sundialMidday.trueSolarNoon();

        System.out.println("\nMidday in true solar time will occur at "
                + "(in local time, with daylight savings if applicable):\n"
                + middayTrueSolarTime);

        scanner.close();
        return middayTrueSolarTime;
    }

    // Deals with NumberFormatException from converting String
    // with padded zeros to integer
    private int removePaddedZeros(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return Integer.valueOf(str.substring(1));
        }
    }

    // Parses and puts city data from cityData.csv into
    // a HashMap
    private void loadCityDataFile() {
        try (Scanner fileScan = new Scanner(Paths.get("cityData.csv"))) {

            fileScan.useDelimiter("[,\\n]");

            // Skip header of file.
            fileScan.nextLine();

            while (fileScan.hasNext()) {

                String city = fileScan.next().toLowerCase();
                fileScan.next(); // Skip latitude.
                double longitude = fileScan.nextDouble();
                fileScan.nextLine(); // Skip rest of line.

                // Longitude longitude = new
                // Longitude(fileScan.nextDouble(), fileScan.next());
                citiesAndLongitudes.put(city, longitude);
            }
        } catch (IOException e) {
            System.out.println("A file error has occurred."
                    + " Exiting program.");
            System.exit(1);
        }
    }
}
