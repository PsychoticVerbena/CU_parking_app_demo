package com.example.cuparkingappdemo;

public class ParkingLot {
    private String name;
    private int availableSpots;

    public ParkingLot(String name, int availableSpots) {
        this.name = name;
        this.availableSpots = availableSpots;
    }

    public String getName() {
        return name;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }
}
