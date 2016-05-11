package com.example.dell.round3.Activity.Maps;



import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.example.dell.round3.Dialogs.ImageDialog;
import com.example.dell.round3.FirebaseModels.Activity;
import com.example.dell.round3.FirebaseModels.Data;
import com.example.dell.round3.FirebaseModels.Submit;
import com.example.dell.round3.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class ViewActivityMapFragment extends MapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener{

    GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;

    private Submit submit;
    private Activity activity;
    private android.support.v4.app.FragmentManager fragmentManager;


    public void setSubmit(Submit submit){
        this.submit = submit;
    }

    public  void setActivity(Activity activity){
        this.activity = activity;
    }

    public void  drawRoute(){
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.GREEN);
        polylineOptions.geodesic(true);
        for(String coordinate: submit.getCoordinates()){
            polylineOptions.add(new LatLng(Double.parseDouble(coordinate.split(",")[0]),Double.parseDouble(coordinate.split(",")[1])));
        }
        mGoogleMap.addPolyline(polylineOptions);
    }

    public void drawActivity(){
        mGoogleMap.clear();

        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(activity.getLatitude(),activity.getLongitude()))   //set center
                .radius(activity.getRadius())   //set radius in meters
                .fillColor(0x5558D3F7)
                .strokeColor(0x10000000)
                .strokeWidth(5);
        mGoogleMap.addCircle(circleOptions);
    }

    public void setMarkers(){
        if(submit.getImages() != null){
            for(Data image : submit.getImages()){
                setCameraMarker(new LatLng(Double.parseDouble(image.getCoordinate().split(",")[0]),Double.parseDouble(image.getCoordinate().split(",")[1])),image.getValue());
            }
        }
        if(submit.getAudios() != null) {
            for (Data audio : submit.getAudios()) {
                setMicMarker(new LatLng(Double.parseDouble(audio.getCoordinate().split(",")[0]), Double.parseDouble(audio.getCoordinate().split(",")[1])),audio.getValue());
            }
        }
        if(submit.getTexts() != null) {
            for (Data text : submit.getTexts()) {
                setTextMarker(new LatLng(Double.parseDouble(text.getCoordinate().split(",")[0]), Double.parseDouble(text.getCoordinate().split(",")[1])),text.getValue());
            }
        }
    }

    public void setCameraMarker(LatLng latLng, String tag) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(tag);
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.camera)));
        mGoogleMap.addMarker(options);
    }

    public void setTextMarker(LatLng latLng, String tag) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(tag);
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.message_text)));
        mGoogleMap.addMarker(options);
    }

    public void setMicMarker(LatLng latLng, String tag) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(tag);
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.microphone)));
        mGoogleMap.addMarker(options);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        this.getMapAsync(this);
    }

    private void initListeners() {
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener( this );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(false);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        initListeners();
        drawActivity();
        drawRoute();
        setMarkers();
    }

    private void initCamera(LatLng location) {
        CameraPosition position = CameraPosition.builder()
                .target(location)
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        mGoogleMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initCamera(new LatLng(activity.getLatitude(),activity.getLongitude()));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String title = marker.getTitle();
        if(title.contains("http://i.imgur.com/")){
            Bundle args = new Bundle();
            args.putSerializable("url", title);
            DialogFragment dialog = new ImageDialog();
            dialog.setArguments(args);
            ViewActivityMapFragment.this.getFragmentManager();
            dialog.show(fragmentManager,"dialog");
        }else{
            if(title.contains("http://www.coderefer.com/extras/uploads/")){

            }else{

            }
        }
        return false;
    }


    public void setFragmentManager(android.support.v4.app.FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
