package com.example.dell.round3;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePicture {

    private static final String PICTURE_FOLDER = "Proyecto/Pictures";
    private static final String PICTURE_FILE_EXT = ".jpg";
    private Context context;

    public TakePicture(Context context) {
        this.context = context;
    }

    public void take(){
        String picture = getFileName();
        File myPicture = new File(picture);
        try {
            myPicture.createNewFile();
            Uri uri = Uri.fromFile(myPicture);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            ((Activity)context).startActivityForResult(cameraIntent, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getFileName(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, PICTURE_FOLDER);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "IMG_" + timeStamp;
        if (!file.exists()) {
            file.mkdirs();
        }
        return (file.getAbsolutePath() + "/" + fileName +  PICTURE_FILE_EXT);
    }

}
