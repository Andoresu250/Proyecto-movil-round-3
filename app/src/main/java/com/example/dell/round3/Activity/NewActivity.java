package com.example.dell.round3.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.dell.round3.Activity.Maps.NewActivityMap;
import com.example.dell.round3.FirebaseModels.Activity;
import com.example.dell.round3.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewActivity extends AppCompatActivity {

    SeekBar radiusSeekBar;
    NewActivityMap map;
    int radius;
    private Firebase root;
    private String firebaseUrl = "https://proyecto-movil.firebaseio.com/activities/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_new);
        root = new Firebase(firebaseUrl);
        map = (NewActivityMap) getFragmentManager().findFragmentById(R.id.newMap);

        radiusSeekBar = (SeekBar) findViewById(R.id.radiusSeekBar);
        radiusSeekBar.setEnabled(false);
        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                radius = i;
                map.changeRadius(radius);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button send = (Button) findViewById(R.id.createActivityBtn);
        send.setEnabled(false);
        assert send != null;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String activityName = ((EditText)findViewById(R.id.activityNameet)).getText().toString();
                if("".equals(activityName)){
                    Toast.makeText(NewActivity.this, "El nombre de la actividad esta vacio, por favor escriba uno.", Toast.LENGTH_SHORT).show();
                }else{
                    root.child(activityName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() == null){
                                Activity activity = new Activity();
                                activity.setName(activityName);
                                activity.setRadius(radius);
                                activity.setLatitude(map.markLatLng.latitude);
                                activity.setLongitude(map.markLatLng.longitude);
                                String note = activity.getRadius() + "m" + " alrededor de la " + map.getAddressFromLatLng(map.markLatLng);
                                activity.setNote(note);
                                root.child(activity.getName()).setValue(activity);
                                Toast.makeText(NewActivity.this, "La actividad " + activityName + " fue creada exitosamente", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(NewActivity.this,ActivitiesActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(NewActivity.this, "Ya existe una actividad con este nombre, por favor elija otro.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }
        });

    }

}
