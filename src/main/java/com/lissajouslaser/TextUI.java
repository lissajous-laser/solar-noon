package com.lissajouslaser;

import java.io.File;
import java.io.IOException;
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

    public TextUI() {
        citiesAndLongitudes = new HashMap<>();
        cities = new ArrayList<String>();
    }

    /**
     * Main public method for creating the user interface.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Calendar cal = Calendar.getInstance();
        String input;

        while (true) {
            loadFile();

            System.out.println("This program will give you the time of solar noon.");
            System.out.println("Here are the cities you can check the time for:");
            for (String city : cities) {
                System.out.println("- " + city);
            }
            System.out.println("Enter in a city:");
            input = scanner.nextLine().trim();

            Longitude longitude = citiesAndLongitudes.get(input);
            if (longitude != null) {
                break;
            }
            System.out.println("Sorry there is no matching city. Try again.");
        }

        while (true) {
            System.out.println("Enter a date (in YYMMDD format) for the date "
                    + "you would like to check. Leave blank for today.");
            System.out.println("Calculation is most accurate for dates between"
                    + " the years 1900 to 2100");
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                break;
            }
            if (input.matches("\\d{6}")) {
                // will allow lenient mode on inputs
                final int monthIndex = 2;
                final int dayIndex = 4;
                cal = Calendar.getInstance();
                cal.set(Integer.valueOf(input.substring(0, monthIndex)),
                        Integer.valueOf(input.substring(monthIndex, dayIndex)),
                        Integer.valueOf(input.substring(dayIndex)));
            }
        }
        scanner.close();
    }

    // parses and puts city data from .csv into
    // a HashMap
    private void loadFile() {
        try (Scanner fileScan = new Scanner(new File("cityLongitudes.csv"))) {

            while (fileScan.hasNext()) {
                String[] line = fileScan.nextLine().split(",");

                String city = line[1];

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
