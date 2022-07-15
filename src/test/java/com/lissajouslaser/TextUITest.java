package com.lissajouslaser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the TextUI class.
 */
public class TextUITest {
    @Test
    public void testCorrectInputsWorks1() {
        try (Scanner scanner = new Scanner(Paths.get("simKeyboardInput1.txt"))) {
            TextUI textUI = new TextUI(scanner);
            assertTrue(textUI.start().contains("12:25"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testCorrectInputsWorks2() {
        try (Scanner scanner = new Scanner(Paths.get("simKeyboardInput2.txt"))) {
            TextUI textUI = new TextUI(scanner);
            assertTrue(textUI.start().contains("11:44")); // earlieset midday London
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
