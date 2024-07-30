package com.zaprnt.beans.enums;

public enum Category {
    ELECTRONICS("Electronics"),
    FURNITURE("Furniture"),
    SPORTS_EQUIPMENT("Sports Equipment"),
    CAMPING_GEARS("Camping Gears"),
    SMARTPHONE("Smartphone");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
