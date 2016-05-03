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

    public void insertCoordinates(TCoordinates coordinates){
        db.execSQL(TCoordinates.CREATE_DB_TABLE);
        db.insert(TCoordinates.TABLE_NAME,null,generateValues(coordinates));
    }

    public void deleteCoordinates(){
        db.rawQuery("DROP TABLE IF EXISTS coordinates" ,null);
        db.rawQuery("DROP TABLE IF EXISTS " + TCoordinates.TABLE_NAME ,null);
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TCoordinates.CREATE_DB_TABLE);
        System.out.println(">>>>>>" + TCoordinates.CREATE_DB_TABLE);
        System.out.println(">>>>>> SE CREO LA TABLA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TCoordinates.CREATE_DB_TABLE);
        System.out.println(">>>>>>" + TCoordinates.CREATE_DB_TABLE);
        System.out.println(">>>>>> SE CREO LA TABLA");
    }
}
