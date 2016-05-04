package com.example.dell.round3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.example.dell.round3.Models.MyDataBase;
import com.example.dell.round3.Models.TCoordinates;
import com.example.dell.round3.Models.TFiles;
import com.example.dell.round3.Models.TMarkers;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyMapFragment extends MapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    LatLng latLng;
    GoogleMap mGoogleMap;
    ArrayList<LatLng> coordinates = new ArrayList<>();
    MyDataBase myDataBase;
    String firebaseUrl = "https://proyecto-movil.firebaseio.com/";
    Firebase root;

    //ACTIVITY PARAMS
    String activityName;
    float radius;
    int activityId;
    int userId;
    double activityLat;
    double activitylong;

    String[] types = {"image","text","audio"};



    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Firebase.setAndroidContext(getActivity().getApplicationContext());
        root = new Firebase(firebaseUrl);

        activityName = "test";
        radius = 10;
        activityId = 1;
        userId = 1;
        activityLat = 0;
        activitylong = 0;

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        this.getMapAsync(this);
        myDataBase = new MyDataBase(getActivity().getApplicationContext());
    }

    public void setCameraMarket() {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Foto");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.camera)));
        mGoogleMap.addMarker(options);
        String token = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+"";
        TMarkers marker = new TMarkers(activityId,userId,(float)latLng.latitude,(float)latLng.longitude, token, types[0]);
        myDataBase.insertMarkers(marker);
    }

    public void setTextMarket() {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Texto");

        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.message_text)));
        mGoogleMap.addMarker(options);
        String token = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+"";
        TMarkers marker = new TMarkers(activityId,userId,(float)latLng.latitude,(float)latLng.longitude, token, types[1]);
        myDataBase.insertMarkers(marker);
    }

    public void setMicMarket() {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Audio");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.microphone)));
        mGoogleMap.addMarker(options);
        String token = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+"";
        TMarkers marker = new TMarkers(activityId,userId,(float)latLng.latitude,(float)latLng.longitude, token, types[2]);
        myDataBase.insertMarkers(marker);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getActivity().getApplicationContext(), "Conectado", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            coordinates.add(latLng);
            String token = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+"";
            TCoordinates coordinate = new TCoordinates(activityId,userId,(float)latLng.latitude,(float)latLng.longitude, token);
            myDataBase.insertCoordinates(coordinate);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            initCamera(mLastLocation);
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        coordinates.add(latLng);
        String token = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+"";
        TCoordinates coordinate = new TCoordinates(activityId,userId,(float)latLng.latitude,(float)latLng.longitude, token);
        myDataBase.insertCoordinates(coordinate);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(16).build();
        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        drawRoute(coordinates);
    }

    public void drawRoute(ArrayList<LatLng> coordinates){
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.GREEN);
        polylineOptions.geodesic(true);
        for (LatLng coordinate : coordinates){
            polylineOptions.add(coordinate);
        }
        mGoogleMap.addPolyline(polylineOptions);
    }

    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        mGoogleMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    public void addMark(LatLng latLng, String title) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(title);
        options.icon(BitmapDescriptorFactory.defaultMarker());
        mGoogleMap.addMarker(options);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}

