package com.example.dell.round3.GetResources;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.dell.round3.DB.DataBase;
import com.example.dell.round3.DB.TData;
import com.example.dell.round3.Login.CurrentUser;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePicture {

    private static final String PICTURE_FOLDER = "Proyecto/Pictures";
    private static final String PICTURE_FILE_EXT = ".jpg";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private Context context;
    private com.example.dell.round3.FirebaseModels.Activity activity;
    private CurrentUser user;

    public TakePicture(Context context, com.example.dell.round3.FirebaseModels.Activity activity) {
        this.context = context;
        this.activity = activity;
    }



    public void take(){
        String picture = getFileName();
        File myPicture = new File(picture);
        user = new CurrentUser(context);
        try {
            myPicture.createNewFile();
            Uri uri = Uri.fromFile(myPicture);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            ((Activity)context).startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            //TODO: poner esto en el onResultActivity
            DataBase db = new DataBase(context);
            TData imageD = new TData(activity.getName(),user.getName(),picture,"image");
            db.insertData(imageD);

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
