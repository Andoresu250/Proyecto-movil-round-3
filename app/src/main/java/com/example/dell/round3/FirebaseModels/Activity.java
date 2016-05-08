package com.example.dell.round3.FirebaseModels;


import java.util.ArrayList;

public class Activity {
    String name;
    double latitude;
    double longitude;
    double radius;
    String note;
    ArrayList<Submit> submits;


    public Activity(String name, double latitude, double longitude, double radius, String note) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.note = note;
    }

    public Activity() {
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
