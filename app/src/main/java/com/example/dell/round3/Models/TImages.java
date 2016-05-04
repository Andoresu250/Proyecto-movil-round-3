package com.example.dell.round3.Models;

public class TImages {

    public static final String TABLE_NAME = "images_files";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_URL = "url";
    public static final String CREATE_DB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            FIELD_ID + " INTEGER primary key autoincrement, " +
            FIELD_URL + " TEXT " +
            ")";

    private int id;
    private String url;

    public TImages(String url) {
        this.url = url;
    }

    public TImages() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
