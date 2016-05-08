package com.example.dell.round3.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.dell.round3.Activity.Maps.NewActivityMap;
import com.example.dell.round3.FirebaseModels.Activity;
import com.example.dell.round3.R;
import com.firebase.client.Firebase;

import java.util.HashMap;

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
        assert send != null;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Agregar validacion de nombre (si este ya existe)
                // TODO: Verfiicar el marcador antes de enviar
                Activity activity = new Activity();
                activity.setName(((EditText)findViewById(R.id.activityNameet)).getText().toString());
                activity.setRadius(radius);
                activity.setLatitude(map.markLatLng.latitude);
                activity.setLongitude(map.markLatLng.longitude);
                String note = activity.getRadius() + "m" + " alrededor de la " + map.getAddressFromLatLng(map.markLatLng);
                activity.setNote(note);
                root.child(activity.getName()).setValue(activity);
                Intent i = new Intent(NewActivity.this,ActivitiesActivity.class);
                startActivity(i);
            }
        });

    }
}
