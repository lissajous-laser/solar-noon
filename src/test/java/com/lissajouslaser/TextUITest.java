package com.lissajouslaser;

import java.time.LocalTime;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the TextUI class.
 */
public class TextUITest {
    private final int varianceSecs = 90;

    @Test
    public void testCorrectInputsWorks1() {
        final LocalTime expectedNoon = LocalTime.of(12, 25);
        String simKeyboardInput =
                "Melbourne\n"
                + "140722\n" // incorrect entry
                + "1407";    // retried entry

        Scanner scanner = new Scanner(simKeyboardInput);
        TextUI textUI = new TextUI(scanner);

        assertTrue(
                Math.abs(textUI.start().toSecondOfDay() - expectedNoon.toSecondOfDay())
                < varianceSecs
        );
    }

    @Test
    public void testCorrectInputsWorks2() {
        final LocalTime expectedNoon = LocalTime.of(11, 44);
        String simKeyboardInput =
                "St. Petersburg\n" // incorrect entry
                + "Londres\n"      // incorrect entry
                + "London\n"
                + "0211\n";

        Scanner scanner = new Scanner(simKeyboardInput);
        TextUI textUI = new TextUI(scanner);

        assertTrue(
                Math.abs(textUI.start().toSecondOfDay() - expectedNoon.toSecondOfDay())
                < varianceSecs
        );
    }
}
