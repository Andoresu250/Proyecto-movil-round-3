package com.example.dell.round3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;

import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import android.widget.Toast;

import com.example.dell.round3.ApiImgur.imgurmodel.ImageResponse;
import com.example.dell.round3.ApiImgur.imgurmodel.Upload;
import com.example.dell.round3.ApiImgur.services.UploadService;
import com.example.dell.round3.Models.MyDataBase;
import com.example.dell.round3.Models.TCoordinates;
import com.example.dell.round3.Models.TFiles;
import com.example.dell.round3.Models.TMarkers;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private AudioRecording audioRecording;
    private TakePicture takePicture;
    private String firebaseUrl = "https://proyecto-movil.firebaseio.com/";
    private Firebase root;
    private MyDataBase myDataBase;
    public MyMapFragment myMapFragment;

    //ACTIVITY PARAMS
    String activityName;
    float radius;
    int activityId;
    int userId;
    double activityLat;
    double activitylong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        root = new Firebase("https://proyecto-movil.firebaseio.com/");
        Map<String, String> asd = new HashMap<>();
        asd.put("hola",":v");
        Firebase asd2 = root.push();
        asd2.setValue(asd);
        asd2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("asd",dataSnapshot.getRef().toString());
                Log.d("asd",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        activityName = "test";
        radius = 10;
        activityId = 1;
        userId = 1;
        activityLat = 0;
        activitylong = 0;

        myDataBase = new MyDataBase(this);

        takePicture = new TakePicture(this, activityId, userId);

        myMapFragment = (MyMapFragment) getFragmentManager().findFragmentById(R.id.map);

        audioRecording = new AudioRecording(myMapFragment,activityId,userId);

        final FloatingActionButton buttonRecord = (FloatingActionButton) findViewById(R.id.action_record);
        assert buttonRecord != null;
        buttonRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // startRecording();
                    Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    Handler h = new Handler();
                    h.postDelayed(new Runnable(){@Override public void run(){}}, 100);
                    buttonRecord.setTitle("Grabando...");
                    buttonRecord.setIcon(R.drawable.ic_my_mic_rec);
                    audioRecording.starRecording();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    Toast.makeText(MainActivity.this, "Grabado finalizado", Toast.LENGTH_SHORT).show();
                    buttonRecord.setTitle("Manten presionado para grabar");
                    buttonRecord.setIcon(R.drawable.ic_my_mic);
                    //stop
                    audioRecording.stopRecording();
                }
                return true;
            }
        });
        final FloatingActionButton buttonCamera = (FloatingActionButton) findViewById(R.id.action_open_camera);
        assert buttonCamera != null;
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture.take();
            }
        });
        final FloatingActionButton buttonText = (FloatingActionButton) findViewById(R.id.action_text);
        assert buttonText != null;
        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //myMapFragment.setTextMarket();
                Bundle args = new Bundle();
                args.putInt("activityId",activityId);
                args.putInt("userId",userId);
                DialogFragment dialog = new TextDialog();
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
        final FloatingActionButton buttonUpload = (FloatingActionButton) findViewById(R.id.action_upload);
        assert buttonUpload != null;
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnectedViaWifi()) {
                    // Your code here
                    Toast.makeText(MainActivity.this, "Enviando...", Toast.LENGTH_SHORT).show();
                    ArrayList<Upload> uploads = new ArrayList<Upload>();
                    ArrayList<TFiles> images = myDataBase.getImages(activityId+"",userId+"");
                    for (TFiles image: images) {
                        uploads.add(new Upload(new File(image.getFile())));
                    }
                    String[] types = {"image","text","audio"};
                    new UploadService(MainActivity.this).Execute(uploads, new UiCallback(),MainActivity.this,root,types,activityName,activityId,userId);

                }else{
                    DialogFragment dialog = new MyAlertDialog();
                    dialog.show(getSupportFragmentManager(), "dialog");
                }
            }
        });

    }
    private boolean isConnectedViaWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
                System.out.println(">>>> LA FOTO SE TOMO");
                myMapFragment.setCameraMarket();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Toast.makeText(MainActivity.this, "Error en la coneccion", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void success(ImageResponse imageResponse, Response response) {

        }
    }

}
