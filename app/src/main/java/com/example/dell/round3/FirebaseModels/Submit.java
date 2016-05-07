package com.example.dell.round3.FirebaseModels;

import java.util.ArrayList;

public class Submit {
    ArrayList<String> coordinates;
    ArrayList<String> images;
    ArrayList<String> audios;
    ArrayList<String> texts;
    ArrayList<Marker> markers;
    int studentId;

    public Submit(ArrayList<String> coordinates, ArrayList<String> images, ArrayList<String> audios, ArrayList<String> texts, ArrayList<Marker> markers, int studentId) {
        this.coordinates = coordinates;
        this.images = images;
        this.audios = audios;
        this.texts = texts;
        this.markers = markers;
        this.studentId = studentId;
    }

    public Submit() {

    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public ArrayList<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<String> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<String> audios) {
        this.audios = audios;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<String> texts) {
        this.texts = texts;
    }

    public ArrayList<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(ArrayList<Marker> markers) {
        this.markers = markers;
    }
}
