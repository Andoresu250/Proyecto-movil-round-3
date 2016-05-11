package com.example.dell.round3.FirebaseModels;

import java.io.Serializable;
import java.util.ArrayList;

public class Submit implements Serializable {
    ArrayList<String> coordinates;
    ArrayList<Data> audios;
    ArrayList<Data> images;
    ArrayList<Data> texts;
    String studentName;

    public Submit(ArrayList<String> coordinates, ArrayList<Data> audios, ArrayList<Data> images, ArrayList<Data> texts, String studentName) {
        this.coordinates = coordinates;
        this.audios = audios;
        this.images = images;
        this.texts = texts;
        this.studentName = studentName;
    }

    public ArrayList<Data> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<Data> audios) {
        this.audios = audios;
    }

    public ArrayList<Data> getImages() {
        return images;
    }

    public void setImages(ArrayList<Data> images) {
        this.images = images;
    }

    public ArrayList<Data> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<Data> texts) {
        this.texts = texts;
    }

    public Submit() {

    }

    public ArrayList<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<String> coordinates) {
        this.coordinates = coordinates;
    }


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
