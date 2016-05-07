package com.example.dell.round3.FirebaseModels;


import java.util.ArrayList;

public class Activity {
    String name;
    int id;
    double latitude;
    double longitude;
    double radius;
    ArrayList<Submit> submits;


    public Activity(String name, int id, double latitude, double longitude, double radius) {
        this.name = name;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public Activity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public ArrayList<Submit> getSubmits() {
        return submits;
    }

    public void setSubmits(ArrayList<Submit> submits) {
        this.submits = submits;
    }
}
