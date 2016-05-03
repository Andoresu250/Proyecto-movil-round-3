package com.example.dell.round3.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_tag_app";
    private static final int SCHEME_VERSION = 3;
    private SQLiteDatabase db;

    public MyDataBase(Context context) {
        super(context, DB_NAME, null, SCHEME_VERSION);
        db = this.getWritableDatabase();
    }

    private ContentValues generateValues(TCoordinates coordinates){
        ContentValues values = new ContentValues();
        values.put(TCoordinates.FIELD_ACTIVITY_ID, coordinates.getActivity_id());
        values.put(TCoordinates.FIELD_USER_ID, coordinates.getUser_id());
        values.put(TCoordinates.FIELD_LAT, coordinates.getLat());
        values.put(TCoordinates.FIELD_LOG, coordinates.getLog());
        values.put(TCoordinates.FIELD_TOKEN, coordinates.getToken());
        return values;
    }

    private ContentValues generateValues(TMarkers marker){
        ContentValues values = new ContentValues();
        values.put(TMarkers.FIELD_ACTIVITY_ID, marker.getActivity_id());
        values.put(TMarkers.FIELD_USER_ID, marker.getUser_id());
        values.put(TMarkers.FIELD_LAT, marker.getLat());
        values.put(TMarkers.FIELD_LOG, marker.getLog());
        values.put(TMarkers.FIELD_TOKEN, marker.getToken());
        values.put(TMarkers.FIELD_TYPE, marker.getType());
        return values;
    }

    public void insertMarkers(TMarkers marker){
        db.execSQL(TMarkers.CREATE_DB_TABLE);
        db.insert(TMarkers.TABLE_NAME,null,generateValues(marker));
    }

    public void insertCoordinates(TCoordinates coordinates){
        db.execSQL(TCoordinates.CREATE_DB_TABLE);
        db.insert(TCoordinates.TABLE_NAME,null,generateValues(coordinates));
    }

    public void deleteCoordinates(){
        db.rawQuery("DROP TABLE IF EXISTS coordinates" ,null);
        db.rawQuery("DROP TABLE IF EXISTS " + TCoordinates.TABLE_NAME ,null);
    }

    public void deleteMarkers(){
        db.rawQuery("DROP TABLE IF EXISTS " + TMarkers.TABLE_NAME ,null);
    }

    public ArrayList<TCoordinates> getCoordinates(String activityId, String userId){
        ArrayList<TCoordinates> coordinates = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TCoordinates.TABLE_NAME + " WHERE " + TCoordinates.FIELD_ACTIVITY_ID + " = " + activityId + " AND " + TCoordinates.FIELD_USER_ID + " = " + userId,null);
        if(c.moveToFirst()){
            do{
                TCoordinates coordinate = new TCoordinates();
                coordinate.setId(c.getInt(0));
                coordinate.setActivity_id(c.getInt(1));
                coordinate.setUser_id(c.getInt(2));
                coordinate.setLat(c.getFloat(3));
                coordinate.setLog(c.getFloat(4));
                coordinate.setToken(c.getString(5));
                coordinates.add(coordinate);
            }while(c.moveToNext());
        }
        return coordinates;
    }

    public ArrayList<TMarkers> getMarkets(String activityId, String userId, String type){
        ArrayList<TMarkers> markets = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TMarkers.TABLE_NAME + " WHERE " +
                TMarkers.FIELD_ACTIVITY_ID + " = " + activityId + " AND " +
                TMarkers.FIELD_USER_ID + " = " + userId +
                TMarkers.FIELD_TYPE + " = " + type
                ,null);
        if(c.moveToFirst()){
            do{
                TMarkers marker = new TMarkers();
                marker.setId(c.getInt(0));
                marker.setActivity_id(c.getInt(1));
                marker.setUser_id(c.getInt(2));
                marker.setLat(c.getFloat(3));
                marker.setLog(c.getFloat(4));
                marker.setToken(c.getString(5));
                marker.setType(c.getString(6));
                markets.add(marker);
            }while(c.moveToNext());
        }
        return markets;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TCoordinates.CREATE_DB_TABLE);
        db.execSQL(TMarkers.CREATE_DB_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TCoordinates.CREATE_DB_TABLE);
        db.execSQL(TMarkers.CREATE_DB_TABLE);

    }
}
