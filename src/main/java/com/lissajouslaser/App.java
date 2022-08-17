package com.lissajouslaser;

/**
 * Class for executing program.
 */
public final class App {
    private App() {
    }

    /**
     * Entry point of program.
     */
    public static void main(String[] args) {
        TextUI textUI = new TextUI();
        textUI.start();
    }
}
