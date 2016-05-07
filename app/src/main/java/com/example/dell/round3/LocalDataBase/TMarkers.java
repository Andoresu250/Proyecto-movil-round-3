package com.example.dell.round3.LocalDataBase;


public class TMarkers {
    public static final String TABLE_NAME = "map_markers";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_ACTIVITY_ID = "activity_id";
    public static final String FIELD_USER_ID = "user_id";
    public static final String FIELD_LOG = "log";
    public static final String FIELD_LAT = "lat";
    public static final String FIELD_TOKEN = "token";
    public static final String FIELD_TYPE= "type";
    public static final String CREATE_DB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            FIELD_ID + " INTEGER primary key autoincrement, " +
            FIELD_ACTIVITY_ID + " INTEGER, " +
            FIELD_USER_ID + " INTEGER, " +
            FIELD_LAT + " FLOAT, " +
            FIELD_LOG + " FLOAT, " +
            FIELD_TOKEN + " TEXT, " +
            FIELD_TYPE + " TEXT " +
            ")";

    private int id;
    private int activity_id;
    private int user_id;
    private float lat;
    private float log;
    private String token;
    private String type;

    public TMarkers(int activity_id, int user_id, float lat, float log, String token, String type) {
        this.activity_id = activity_id;
        this.user_id = user_id;
        this.lat = lat;
        this.log = log;
        this.token = token;
        this.type = type;
    }

    public TMarkers() {

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ""+this.lat + ","+this.log;
    }
}