package com.lissajouslaser;

import java.util.Calendar;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        SundialMidday sundialMidday = new SundialMidday();
        Calendar cal = Calendar.getInstance();

        String midday = sundialMidday.trueSolarTime("New York", cal);
        System.out.println(midday);
    }
}
