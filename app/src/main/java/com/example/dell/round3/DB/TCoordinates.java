package com.example.dell.round3.DB;


public class TCoordinates {

    public static final String TABLE_NAME = "map_coordinates";
    public static final String FIELD_ACTIVITY_NAME = "activity_name";
    public static final String FIELD_USER_NAME = "user_name";
    public static final String FIELD_COORDINATE = "coordinate";
    public static final String CREATE_DB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            FIELD_ACTIVITY_NAME + " TEXT, " +
            FIELD_USER_NAME + " TEXT, " +
            FIELD_COORDINATE + " TEXT " +
            ")";

    private String activity_name;
    private String user_name;
    private String coorinate;

    public TCoordinates(String activity_name, String user_name, String coorinate) {
        this.activity_name = activity_name;
        this.user_name = user_name;
        this.coorinate = coorinate;
    }

    public TCoordinates() {
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCoorinate() {
        return coorinate;
    }

    public void setCoorinate(String coorinate) {
        this.coorinate = coorinate;
    }
}
