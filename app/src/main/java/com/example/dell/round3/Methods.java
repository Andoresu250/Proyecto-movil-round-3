package com.example.dell.round3;


import android.content.Context;
import android.widget.Toast;

import com.example.dell.round3.LocalDataBase.MyDataBase;
import com.example.dell.round3.LocalDataBase.TCoordinates;
import com.example.dell.round3.LocalDataBase.TFiles;
import com.example.dell.round3.LocalDataBase.TImages;
import com.example.dell.round3.LocalDataBase.TMarkers;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Methods {

     public static void submitActivity(Context context, Firebase root, String[] types, String activityName, int activityId, int userId){
        MyDataBase myDataBase = new MyDataBase(context);
        //Getting activity information
        Firebase activityRef = root.child("activities").child(activityName);
        //Adding and getting data
        Firebase dataActivityRef = activityRef.child("data");

        //sending all coordinates to describe route
        Map<String, String> postCoordinates = new HashMap<String, String>();
        ArrayList<TCoordinates> coordinates = myDataBase.getCoordinates(activityId+"",userId+"");
        for (TCoordinates coordinate: coordinates) {
            postCoordinates.put(coordinate.getToken(),coordinate.toString());
        }

        /*Map<String, String> postImages = new HashMap<String, String>();
        ArrayList<TFiles> images = myDataBase.getImages(activityId+"",userId+"");
        for (TFiles file: images) {
            postImages.put(file.getToken(),file.getFile());
        }*/

         //add all urls and put on Map to firebase
         Map<String, String> postImages = new HashMap<String, String>();
         ArrayList<TImages> images = myDataBase.getImagesUrl();
         for (TImages image: images) {
             postImages.put(image.getId()+"",image.getUrl());
         }

         //add all texts and put on Map to firebase
        Map<String, String> postText = new HashMap<String, String>();
        ArrayList<TFiles> text = myDataBase.getFiles(activityId+"",userId+"",types[1]);
        for (TFiles file: text) {
            postText.put(file.getToken(),file.getFile());
        }
         //add all audios paths and put on Map to firebase
        Map<String, String> postAudios = new HashMap<String, String>();
        ArrayList<TFiles> audios = myDataBase.getAudios(activityId+"",userId+"");
        for (TFiles file: audios) {
            postAudios.put(file.getToken(),file.getFile());
        }

        ArrayList<TMarkers> markers;

        Map<String, String> postMarkerImages = new HashMap<String, String>();
        markers = myDataBase.getMarkets(activityId+"",userId+"", types[0]);
        for (TMarkers marker: markers) {
            postMarkerImages.put(marker.getToken(),marker.toString());
        }

        Map<String, String> postMarkerTexts = new HashMap<String, String>();
        markers = myDataBase.getMarkets(activityId+"",userId+"", types[1]);
        for (TMarkers marker: markers) {
            postMarkerTexts.put(marker.getToken(),marker.toString());
        }

        Map<String, String> postMarkerAudios = new HashMap<String, String>();
        markers = myDataBase.getMarkets(activityId+"",userId+"", types[2]);
        for (TMarkers marker: markers) {
            postMarkerAudios.put(marker.getToken(),marker.toString());
        }

        Firebase dataRef = dataActivityRef.push();

        Firebase coorRef = dataRef.child("coordinates");
        coorRef.setValue(postCoordinates);

        Firebase imagesRef = dataRef.child("images");
        imagesRef.setValue(postImages);

        Firebase audiosRef = dataRef.child("audios");
        audiosRef.setValue(postAudios);

        Firebase textRef = dataRef.child("text");
        textRef.setValue(postText);

        Firebase markersRef;
        markersRef = dataRef.child("markers").child("images");
        markersRef.setValue(postMarkerImages);

        markersRef = dataRef.child("markers").child("texts");
        markersRef.setValue(postMarkerTexts);

        markersRef = dataRef.child("markers").child("audios");
        markersRef.setValue(postMarkerAudios);

        myDataBase.deleteCoordinates();
        myDataBase.deleteMarkers();
        myDataBase.deleteFiles();
        myDataBase.deleteImages();
        context.deleteDatabase(myDataBase.getDatabaseName());
        Toast.makeText(context, "Actividad enviada", Toast.LENGTH_SHORT).show();
    }
}
