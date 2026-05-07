/*
 * Course: SWE2410 - 121
 * Spring 2026
 * Lab 8 - Unicorn Locator
 * Name: Alex Horton
 * Created: 4/21/2025
 */

package com.unicorn.location;

/**
 * Class used as the target of JSON data
 */
public class UnicornLocation {
    private String unicornName;
    private String longitude;
    private String latitude;

    public String getUnicornName() {
        return unicornName;
    }

    public void setUnicornName(String unicornName) {
        this.unicornName = unicornName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}