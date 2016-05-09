package com.example.dell.round3.FirebaseModels;

public class Marker {

    String type;
    String  coordinate;

    public Marker(String type, String coordinate) {
        this.type = type;
        this.coordinate = coordinate;
    }

    public Marker() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}
