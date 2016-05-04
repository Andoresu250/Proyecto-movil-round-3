package com.example.dell.round3.ApiImgur.services;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import com.example.dell.round3.ApiImgur.Constants;
import com.example.dell.round3.ApiImgur.helpers.NotificationHelper;
import com.example.dell.round3.ApiImgur.imgurmodel.ImageResponse;
import com.example.dell.round3.ApiImgur.imgurmodel.ImgurAPI;
import com.example.dell.round3.ApiImgur.imgurmodel.Upload;
import com.example.dell.round3.ApiImgur.utils.NetworkUtils;
import com.example.dell.round3.Methods;
import com.example.dell.round3.Models.MyDataBase;
import com.example.dell.round3.Models.TImages;
import com.firebase.client.Firebase;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class UploadService {
    public final static String TAG = UploadService.class.getSimpleName();

    private WeakReference<Context> mContext;
    private Context context;

    public UploadService(Context context) {
        this.mContext = new WeakReference<>(context);
        this.context = context;
    }

    public void Execute(Upload upload, Callback<ImageResponse> callback) {
        final Callback<ImageResponse> cb = callback;

        if (!NetworkUtils.isConnected(mContext.get())) {
            //Callback will be called, so we prevent a unnecessary notification
            cb.failure(null);
            return;
        }

        final NotificationHelper notificationHelper = new NotificationHelper(mContext.get());
        notificationHelper.createUploadingNotification();

        RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(ImgurAPI.class).postImage(
                Constants.getClientAuth(),
                upload.title,
                upload.description,
                upload.albumId,
                null,
                new TypedFile("image/*", upload.image),
                new Callback<ImageResponse>() {
                    @Override
                    public void success(ImageResponse imageResponse, Response response) {
                        if (cb != null) cb.success(imageResponse, response);
                        if (response == null) {
                            /*
                             Notify image was NOT uploaded successfully
                            */
                            notificationHelper.createFailedUploadNotification();
                            return;
                        }
                        /*
                        Notify image was uploaded successfully
                        */
                        if (imageResponse.success) {
                            notificationHelper.createUploadedNotification(imageResponse);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (cb != null) cb.failure(error);
                        notificationHelper.createFailedUploadNotification();
                    }
                });

    }

    public void Execute(ArrayList<Upload> uploads, Callback<ImageResponse> callback, final Context context, Firebase root, String[] types, String activityName, int activityId, int userId) {
        for(Upload upload : uploads) {

            final Callback<ImageResponse> cb = callback;

            if (!NetworkUtils.isConnected(mContext.get())) {
                //Callback will be called, so we prevent a unnecessary notification
                cb.failure(null);
                return;
            }

            final NotificationHelper notificationHelper = new NotificationHelper(mContext.get());
            notificationHelper.createUploadingNotification();

            RestAdapter restAdapter = buildRestAdapter();

            restAdapter.create(ImgurAPI.class).postImage(
                    Constants.getClientAuth(),
                    upload.title,
                    upload.description,
                    upload.albumId,
                    null,
                    new TypedFile("image/*", upload.image),
                    new Callback<ImageResponse>() {
                        @Override
                        public void success(ImageResponse imageResponse, Response response) {
                            if (cb != null) cb.success(imageResponse, response);
                            if (response == null) {
                                /*
                                 Notify image was NOT uploaded successfully
                                */
                                notificationHelper.createFailedUploadNotification();
                                return;
                            }
                            /*
                            Notify image was uploaded successfully
                            */
                            if (imageResponse.success) {
                                notificationHelper.createUploadedNotification(imageResponse);
                                TImages image = new TImages(imageResponse.data.link);
                                MyDataBase myDataBase = new MyDataBase(context);
                                myDataBase.insertImages(image);
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (cb != null) cb.failure(error);
                            notificationHelper.createFailedUploadNotification();
                        }
                    });
        }
        System.out.println(">>>> Se termino de enviar las fotos");
        Methods.submitActivity(context,root,types,activityName,activityId,userId);
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurAPI.server)
                .build();

        /*
        Set rest adapter logging if we're already logging
        */
        if (Constants.LOGGING)
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;
    }
}
