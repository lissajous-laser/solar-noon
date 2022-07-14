package com.lissajouslaser;

/**
 * Class of storing longitude values.
 */
public class Longitude {
    private double degrees;
    private String direction;

    public Longitude(double degrees, String direction) {
        this.degrees = degrees;
        this.direction = direction;
    }

    public double getDegrees() {
        return degrees;
    }

    public String getDirection() {
        return direction;
    }
}
