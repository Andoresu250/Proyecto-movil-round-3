package com.example.dell.round3.FirebaseModels;


import java.util.ArrayList;

public class Marker {

    String type;
    ArrayList<String>  coordinates;

    public Marker(String type, ArrayList<String> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public Marker() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<String> coordinates) {
        this.coordinates = coordinates;
    }
}
