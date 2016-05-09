package com.example.dell.round3.Activity;

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
import android.view.MotionEvent;
import android.view.View;


import android.widget.Toast;

import com.example.dell.round3.Activity.Maps.ActivityMapFragment;
import com.example.dell.round3.ApiImgur.imgurmodel.ImageResponse;
import com.example.dell.round3.ApiImgur.imgurmodel.Upload;
import com.example.dell.round3.DB.DataBase;
import com.example.dell.round3.Dialogs.MyAlertDialog;
import com.example.dell.round3.FirebaseModels.Activity;
import com.example.dell.round3.GetResources.AudioRecording;
import com.example.dell.round3.GetResources.TakePicture;
import com.example.dell.round3.Dialogs.TextDialog;
import com.example.dell.round3.R;
import com.firebase.client.Firebase;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActivityActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private AudioRecording audioRecording;
    private TakePicture takePicture;
    private String firebaseUrl = "https://proyecto-movil.firebaseio.com/";
    private Firebase root;
    private DataBase db;
    public ActivityMapFragment activityMapFragment;

    //ACTIVITY PARAMS
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        Firebase.setAndroidContext(this);
        root = new Firebase("https://proyecto-movil.firebaseio.com/");

        activity = (Activity) getIntent().getSerializableExtra("activity");

        activityMapFragment = (ActivityMapFragment) getFragmentManager().findFragmentById(R.id.map);
        activityMapFragment.setActivity(activity);

        db = new DataBase(this);

        takePicture = new TakePicture(this, activity);

        audioRecording = new AudioRecording(activityMapFragment, activity);

        final FloatingActionButton buttonRecord = (FloatingActionButton) findViewById(R.id.action_record);
        assert buttonRecord != null;
        buttonRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // startRecording();
                    Vibrator v = (Vibrator) ActivityActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    Handler h = new Handler();
                    h.postDelayed(new Runnable(){@Override public void run(){}}, 100);
                    buttonRecord.setTitle("Grabando...");
                    buttonRecord.setIcon(R.drawable.ic_my_mic_rec);
                    audioRecording.starRecording();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
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

                Bundle args = new Bundle();
                args.putSerializable("activity", activity);
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
                if (isConnectedViaWifi()) {
                    // Your code here
                    Toast.makeText(ActivityActivity.this, "Enviando...", Toast.LENGTH_SHORT).show();
                    ArrayList<Upload> uploads = new ArrayList<Upload>();
                    //TODO: colocar la subida de imagenes y modificarla a como toque
                    /*ArrayList<TFiles> images = myDataBase.getImages(activityId+"",userId+"");
                    for (TFiles image: images) {
                        uploads.add(new Upload(new File(image.getFile())));
                    }
                    String[] types = {"image","text","audio"};
                    new UploadService(ActivityActivity.this).Execute(uploads, new UiCallback(),ActivityActivity.this,root,types,activity);*/

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
                activityMapFragment.setCameraMarker();
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
                Toast.makeText(ActivityActivity.this, "Error en la coneccion", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void success(ImageResponse imageResponse, Response response) {

        }
    }



}
