package com.example.dell.round3.Models;

public class TCoordinates {
    public static final String TABLE_NAME = "map_coordinates";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_ACTIVITY_ID = "activity_id";
    public static final String FIELD_USER_ID = "user_id";
    public static final String FIELD_LOG = "log";
    public static final String FIELD_LAT = "lat";
    public static final String FIELD_TOKEN = "token";
    public static final String CREATE_DB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            FIELD_ID + " INTEGER primary key autoincrement, " +
            FIELD_ACTIVITY_ID + " INTEGER, " +
            FIELD_USER_ID + " INTEGER, " +
            FIELD_LAT + " FLOAT, " +
            FIELD_LOG + " FLOAT, " +
            FIELD_TOKEN + " TEXT " +
            ")";

    private int id;
    private int activity_id;
    private int user_id;
    private float lat;
    private float log;
    private String token;

    public TCoordinates(int activity_id, int user_id, float lat, float log, String token) {
        this.activity_id = activity_id;
        this.user_id = user_id;
        this.lat = lat;
        this.log = log;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TCoordinates() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLog() {
        return log;
    }

    public void setLog(float log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return ""+this.lat + ","+this.log;
    }
}

