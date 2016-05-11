package com.example.dell.round3.FirebaseModels;

import java.io.Serializable;

public class Data implements Serializable {

    private String coordinate;
    private String value;

    public Data(String coordinate, String value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    public Data() {

    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
