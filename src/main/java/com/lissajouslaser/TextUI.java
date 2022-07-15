package com.lissajouslaser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for user interface.
 */
public class TextUI {
    private Map<String, Longitude> citiesAndLongitudes;
    private ArrayList<String> cities;
    private Scanner scanner;

    /**
     * Constructor.
     */
    public TextUI() {
        citiesAndLongitudes = new HashMap<>();
        cities = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    /**
     * Constructor.
     */
    public TextUI(Scanner scanner) {
        citiesAndLongitudes = new HashMap<>();
        cities = new ArrayList<String>();
        this.scanner = scanner;
    }

    /**
     * Main public method for creating the user interface.
     */
    public String start() {

        Calendar cal = Calendar.getInstance();
        Longitude longitude;
        String input;

        loadFile();

        System.out.println("\nThis program will give you the time of midday at "
                + "true solar time");
        System.out.println("Here are the cities you can check the time for:");
        for (String city : cities) {
            System.out.println("- " + city);
        }
        while (true) {

            System.out.println("\nEnter in a city:");
            input = scanner.nextLine().trim();

            longitude = citiesAndLongitudes.get(input);
            if (longitude != null) {
                break;
            }
            System.out.println("Sorry there is no matching city. Try again.");
        }

        System.out.println("\nEnter a date (in MMDD format) "
                + "you would like to check. Leave blank for today.");
        System.out.println("Calculation is most accurate between"
                + " the years 1900 to 2100");

        while (true) {
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                break;
            }
            if (input.matches("\\d{4}")) {
                // Will allow lenient mode on inputs.
                // User input with be one index
                final int dayIndex = 2;
                final int zeroIdxOffset = -1;

                cal = Calendar.getInstance();

                cal.set(0,
                        removePaddedZeros(input.substring(0, dayIndex))
                                + zeroIdxOffset,
                        removePaddedZeros(input.substring(dayIndex))
                                + zeroIdxOffset);
                break;
            }
            System.out.println("Sorry this is not a valid date. Try again.");
        }
        SundialMidday sundialMidday = new SundialMidday(longitude, cal);
        String middayTrueSolarTime = sundialMidday.trueSolarTime();

        System.out.println("\nMidday in true solar time will occur at "
                + "(daylight savings not applied): " + middayTrueSolarTime);

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

    // parses and puts city data from .csv into
    // a HashMap
    private void loadFile() {
        try (Scanner fileScan = new Scanner(Paths.get("cityLongitudes.csv"))) {

            while (fileScan.hasNext()) {
                String[] line = fileScan.nextLine().split(",");

                String city = line[0];

                String[] longitudeTokens = line[1].split("°");
                Longitude longitude = new Longitude(Double
                        .valueOf(longitudeTokens[0]), longitudeTokens[1]);

                cities.add(city);
                citiesAndLongitudes.put(city, longitude);
            }
        } catch (IOException e) {
            System.out.println("A file error has occurred."
                    + " This program will now exit.");
        }
    }
}
