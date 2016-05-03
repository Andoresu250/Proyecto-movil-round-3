package com.example.dell.round3.Models;


public class TFiles {
    public static final String TABLE_NAME = "activity_files";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_ACTIVITY_ID = "activity_id";
    public static final String FIELD_USER_ID = "user_id";
    public static final String FIELD_FILE = "file";
    public static final String FIELD_TOKEN = "token";
    public static final String FIELD_TYPE = "type";
    public static final String CREATE_DB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            FIELD_ID + " INTEGER primary key autoincrement, " +
            FIELD_ACTIVITY_ID + " INTEGER, " +
            FIELD_USER_ID + " INTEGER, " +
            FIELD_FILE + " TEXT, " +
            FIELD_TOKEN + " TEXT, " +
            FIELD_TYPE + " TEXT " +
            ")";
    private int id;
    private int activity_id;
    private int user_id;
    private String file;
    private String token;
    private String type;

    public TFiles() {
    }

    public TFiles(int activity_id, int user_id, String file, String token, String type) {
        this.activity_id = activity_id;
        this.user_id = user_id;
        this.file = file;
        this.token = token;
        this.type = type;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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
}
