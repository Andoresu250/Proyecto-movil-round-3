package com.example.dell.round3.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dell.round3.FirebaseModels.Activity;
import com.example.dell.round3.Login.CurrentUser;
import com.example.dell.round3.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ActivitiesActivity extends AppCompatActivity {

    Firebase root;
    String firebaseUrl = "https://proyecto-movil.firebaseio.com/activities/";
    ListView activitiesList;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Firebase.setAndroidContext(this);
        currentUser = new CurrentUser(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        root = new Firebase(firebaseUrl);
        activitiesList = (ListView) findViewById(R.id.activiesListLV);
        loadActivities();
        System.out.println(">>>>" + currentUser.getType());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(isProfessor()){
            fab.show();
        }else{
            fab.hide();
        }

        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(ActivitiesActivity.this, NewActivity.class);
                startActivity(i);
            }
        });

        activitiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isProfessor()){
                    //TODO: colocar la vista de los submits
                }else{
                    Intent studentIntent = new Intent(ActivitiesActivity.this, ActivityActivity.class);
                    studentIntent.putExtra("activity", (Activity)view.getTag());
                    startActivity(studentIntent);
                }
            }
        });

        //TODO: Set onclickListener para cada elemento deel listView y mostrar dependiendo del tipo de usuario

    }

    private void loadActivities(){
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Activity> activities = new ArrayList<Activity>();
                Activity activity;
                for(DataSnapshot fbActivity : dataSnapshot.getChildren()){
                    activity = new Activity();
                    activity.setName(fbActivity.child("name").getValue().toString());
                    activity.setLatitude(Double.parseDouble(fbActivity.child("latitude").getValue().toString()));
                    activity.setLongitude(Double.parseDouble(fbActivity.child("longitude").getValue().toString()));
                    activity.setRadius(Double.parseDouble(fbActivity.child("radius").getValue().toString()));
                    activity.setNote(fbActivity.child("note").getValue().toString());
                    activities.add(activity);
                }
                ActivityAdapter adapter = new ActivityAdapter(activities, ActivitiesActivity.this);
                activitiesList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private boolean isProfessor(){
        return currentUser.getType().equals("Profesor");
    }

}
