package com.example.dell.round3.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

        public static final String DB_NAME = "Taggealo2";
        private static final int SCHEME_VERSION = 4;
        private SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, DB_NAME, null, SCHEME_VERSION);
        db = this.getWritableDatabase();
    }

    private ContentValues generateValue(TCoordinates coordinate){
        ContentValues values = new ContentValues();
        values.put(TCoordinates.FIELD_ACTIVITY_NAME, coordinate.getActivity_name());
        values.put(TCoordinates.FIELD_USER_NAME, coordinate.getUser_name());
        values.put(TCoordinates.FIELD_COORDINATE, coordinate.getCoorinate());
        return values;
    }

    private ContentValues generateValue(TData data){
        ContentValues values = new ContentValues();
        values.put(TData.FIELD_ACTIVITY_NAME, data.getActivity_name());
        values.put(TData.FIELD_USER_NAME, data.getUser_name());
        values.put(TData.FIELD_VALUE, data.getValue());
        values.put(TData.FIELD_TYPE, data.getType());
        return values;
    }

    public void insertCoordinate(TCoordinates coordinate){
        db.insert(TCoordinates.TABLE_NAME,null,generateValue(coordinate));
    }

    public void insertData(TData data){
        db.insert(TData.TABLE_NAME,null,generateValue(data));
    }

    public ArrayList<TCoordinates> getCoordinates(String activityName, String UserName){
        ArrayList<TCoordinates> coordinates = new ArrayList<>();
        String query = "SELECT * FROM " + TCoordinates.TABLE_NAME + " WHERE " +
                TCoordinates.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TCoordinates.FIELD_USER_NAME + " = " + "'" + UserName + "'";
        Cursor c = db.rawQuery(query ,null);
        if (c.moveToFirst()){
            do {
                TCoordinates coordinate = new TCoordinates();
                coordinate.setActivity_name(c.getString(0));
                coordinate.setUser_name(c.getString(1));
                coordinate.setCoorinate(c.getString(2));
                coordinates.add(coordinate);
            }while (c.moveToNext());
        }
        return coordinates;
    }

    public ArrayList<String> getCoordinatesString(String activityName, String UserName){
        ArrayList<String> coordinates = new ArrayList<>();
        String query = "SELECT * FROM " + TCoordinates.TABLE_NAME + " WHERE " +
                TCoordinates.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TCoordinates.FIELD_USER_NAME + " = " + "'" + UserName + "'";
        Cursor c = db.rawQuery(query ,null);
        if (c.moveToFirst()){
            do {
                coordinates.add(c.getString(2));
            }while (c.moveToNext());
        }
        return coordinates;
    }

    public ArrayList<TData> getImagesMarker(String activityName, String UserName){
        ArrayList<TData> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'imageM'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                TData data = new TData();
                data.setActivity_name(c.getString(0));
                data.setUser_name(c.getString(1));
                data.setValue(c.getString(2));
                data.setType(c.getString(3));
                datas.add(data);
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<String> getImagesMarkerString(String activityName, String UserName){
        ArrayList<String> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'imageM'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                datas.add(c.getString(2));
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<TData> getAudiosMarker(String activityName, String UserName){
        ArrayList<TData> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'audioM'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                TData data = new TData();
                data.setActivity_name(c.getString(0));
                data.setUser_name(c.getString(1));
                data.setValue(c.getString(2));
                data.setType(c.getString(3));
                datas.add(data);
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<String> getAudiosMarkerString(String activityName, String UserName){
        ArrayList<String> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'audioM'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                datas.add(c.getString(2));
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<TData> getTextsMarker(String activityName, String UserName){
        ArrayList<TData> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'textM'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                TData data = new TData();
                data.setActivity_name(c.getString(0));
                data.setUser_name(c.getString(1));
                data.setValue(c.getString(2));
                data.setType(c.getString(3));
                datas.add(data);
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<String> getTextsMarkerString(String activityName, String UserName){
        ArrayList<String> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'textM'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                datas.add(c.getString(2));
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<TData> getAudiosUrl(String activityName, String UserName){
        ArrayList<TData> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'audio'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                TData data = new TData();
                data.setActivity_name(c.getString(0));
                data.setUser_name(c.getString(1));
                data.setValue(c.getString(2));
                data.setType(c.getString(3));
                datas.add(data);
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<String> getAudiosUrlString(String activityName, String UserName){
        ArrayList<String> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'audio'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                datas.add(c.getString(2));
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<TData> getImagesUrl(String activityName, String UserName){
        ArrayList<TData> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'image'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                TData data = new TData();
                data.setActivity_name(c.getString(0));
                data.setUser_name(c.getString(1));
                data.setValue(c.getString(2));
                data.setType(c.getString(3));
                datas.add(data);
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<String> getImagesUrlString(String activityName, String UserName){
        ArrayList<String> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'image'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                datas.add(c.getString(2));
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<TData> getTexts(String activityName, String UserName){
        ArrayList<TData> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'text'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                TData data = new TData();
                data.setActivity_name(c.getString(0));
                data.setUser_name(c.getString(1));
                data.setValue(c.getString(2));
                data.setType(c.getString(3));
                datas.add(data);
            }while (c.moveToNext());
        }
        return datas;
    }

    public ArrayList<String> getTextsString(String activityName, String UserName){
        ArrayList<String> datas = new ArrayList<>();
        String query = "SELECT * FROM " + TData.TABLE_NAME + " WHERE " +
                TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'" + " AND " +
                TData.FIELD_TYPE + " = " + "'text'"
                ;
        Cursor c = db.rawQuery(query,null);
        if (c.moveToFirst()){
            do {
                datas.add(c.getString(2));
            }while (c.moveToNext());
        }
        return datas;
    }

    public void deleteData(String activityName, String UserName){
        db.execSQL("DELETE FROM " + TData.TABLE_NAME +
                " WHERE " + TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                            TData.FIELD_USER_NAME + " = " + "'" + UserName + "'"
            );
    }

    public void deleteCoordinates(String activityName, String UserName){
        db.execSQL("DELETE FROM " + TCoordinates.TABLE_NAME +
                " WHERE " + TData.FIELD_ACTIVITY_NAME + " = " + "'" + activityName + "'" + " AND " +
                TData.FIELD_USER_NAME + " = " + "'" + UserName + "'"
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TCoordinates.CREATE_DB_TABLE);
        db.execSQL(TData.CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TCoordinates.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TData.TABLE_NAME);
        onCreate(db);
    }
}
