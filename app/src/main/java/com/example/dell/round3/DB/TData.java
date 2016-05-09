package com.example.dell.round3.DB;


public class TData {

    public static final String TABLE_NAME = "map_data";
    public static final String FIELD_ACTIVITY_NAME = "activity_name";
    public static final String FIELD_USER_NAME = "user_name";
    public static final String FIELD_VALUE = "value";
    public static final String FIELD_TYPE = "type";
    public static final String CREATE_DB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            FIELD_ACTIVITY_NAME + " TEXT, " +
            FIELD_USER_NAME + " TEXT, " +
            FIELD_VALUE + " TEXT, " +
            FIELD_TYPE + " TEXT " +
            ")";

    private String activity_name;
    private String user_name;
    private String value;
    private String type;

    public TData(String activity_name, String user_name, String value, String type) {
        this.activity_name = activity_name;
        this.user_name = user_name;
        this.value = value;
        this.type = type;
    }

    public TData() {}

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
