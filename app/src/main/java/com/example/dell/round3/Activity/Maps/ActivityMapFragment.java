package com.example.dell.round3.Activity.Maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.example.dell.round3.DB.DataBase;
import com.example.dell.round3.DB.TCoordinates;
import com.example.dell.round3.DB.TData;
import com.example.dell.round3.FirebaseModels.Activity;
import com.example.dell.round3.Login.CurrentUser;
import com.example.dell.round3.R;
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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;


public class ActivityMapFragment extends MapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    LatLng latLng;
    GoogleMap mGoogleMap;
    DataBase db;
    String firebaseUrl = "https://proyecto-movil.firebaseio.com/";
    Firebase root;
    private Activity activity;
    private LatLng activityLatLng;
    public CurrentUser user;

    String[] types = {"image","text","audio"};

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Firebase.setAndroidContext(getActivity().getApplicationContext());
        root = new Firebase(firebaseUrl);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        this.getMapAsync(this);
        db = new DataBase(getActivity().getApplicationContext());
        user = new CurrentUser(getActivity().getApplicationContext());
        ArrayList<TCoordinates> coordinates = db.getALlCoordinates();
        System.out.println(">>>>>>" + coordinates.size());
        for(TCoordinates c : coordinates){
            System.out.println(">>>>" + c.getCoorinate());
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(getActivity().getApplicationContext(), "Conectado", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            activityLatLng = new LatLng(activity.getLatitude(), activity.getLongitude());
            if(isInActivity()) {
                TCoordinates coordinate = new TCoordinates(activity.getName(), user.getName(), latLng.toString().replaceAll("[()]",""));
                db.insertCoordinate(coordinate);
            }
            setActivityOnMap();
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
        setActivityOnMap();
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        if (isInActivity()){
            TCoordinates coordinate = new TCoordinates(activity.getName(),user.getName(),latLng.toString().replaceAll("[()]",""));
            System.out.println(">>>>>TAMAÑO " +db.getALlCoordinates().size());
            db.insertCoordinate(coordinate);
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(16).build();
        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        drawRoute();
    }

    public void drawRoute(){
        //DRAW ROUTE
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.GREEN);
        polylineOptions.geodesic(true);
        ArrayList<TCoordinates> coordinates = db.getCoordinates(activity.getName(),user.getName());
        for (TCoordinates coordinate : coordinates){
            System.out.println(">>>>>" + coordinate.getCoorinate());
            polylineOptions.add(new LatLng(Double.parseDouble(coordinate.getCoorinate().split(",")[0]),Double.parseDouble(coordinate.getCoorinate().split(",")[1])));
        }
        mGoogleMap.addPolyline(polylineOptions);
        //DRAW MARKERS
        ArrayList<TData> imagesM = db.getImagesMarker(activity.getName(), user.getName());
        for(TData imageM : imagesM){
            setCameraMarker(new LatLng(Double.parseDouble(imageM.getValue().split(",")[0]),Double.parseDouble(imageM.getValue().split(",")[1])));
        }
        ArrayList<TData>  audiosM = db.getImagesMarker(activity.getName(), user.getName());
        for(TData audioM : audiosM){
            setMicMarker(new LatLng(Double.parseDouble(audioM.getValue().split(",")[0]),Double.parseDouble(audioM.getValue().split(",")[1])));
        }
        ArrayList<TData> textsM = db.getImagesMarker(activity.getName(), user.getName());
        for(TData textM : textsM){
            setTextMarker(new LatLng(Double.parseDouble(textM.getValue().split(",")[0]),Double.parseDouble(textM.getValue().split(",")[1])));
        }
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
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    public void setCameraMarker() {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Foto");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.camera)));
        mGoogleMap.addMarker(options);
        TData cameraMarker = new TData(activity.getName(),user.getName(),latLng.toString().replaceAll("[()]",""),"imageM");
        db.insertData(cameraMarker);
    }

    public void setTextMarker() {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Texto");

        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.message_text)));
        mGoogleMap.addMarker(options);
        TData textMarker = new TData(activity.getName(),user.getName(),latLng.toString().replaceAll("[()]",""),"textM");
        db.insertData(textMarker);
    }

    public void setMicMarker() {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Audio");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.microphone)));
        mGoogleMap.addMarker(options);
        TData audioMarker = new TData(activity.getName(),user.getName(),latLng.toString().replaceAll("[()]",""),"audioM");
        db.insertData(audioMarker);
    }

    public void setCameraMarker(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Foto");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.camera)));
        mGoogleMap.addMarker(options);
        TData cameraMarker = new TData(activity.getName(),user.getName(),latLng.toString().replaceAll("[()]",""),"imageM");
        db.insertData(cameraMarker);
    }

    public void setTextMarker(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Texto");

        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.message_text)));
        mGoogleMap.addMarker(options);
        TData textMarker = new TData(activity.getName(),user.getName(),latLng.toString().replaceAll("[()]",""),"textM");
        db.insertData(textMarker);
    }

    public void setMicMarker(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title("Audio");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.microphone)));
        mGoogleMap.addMarker(options);
        TData audioMarker = new TData(activity.getName(),user.getName(),latLng.toString().replaceAll("[()]",""),"audioM");
        db.insertData(audioMarker);
    }

    public void setActivityOnMap(){
        mGoogleMap.clear();
        MarkerOptions options = new MarkerOptions().position( activityLatLng );
        options.icon( BitmapDescriptorFactory.defaultMarker() );
        mGoogleMap.addMarker( options );
        CircleOptions circleOptions = new CircleOptions()
                .center(activityLatLng)   //set center
                .radius(activity.getRadius())   //set radius in meters
                .fillColor(0x5558D3F7)
                .strokeColor(0x10000000)
                .strokeWidth(5);
        mGoogleMap.addCircle(circleOptions);
    }

    public boolean isInActivity(){
        if(distFrom(latLng,activityLatLng) <= activity.getRadius()){
            System.out.println(">>>>" + "ESTA");
            return true;
        }
        System.out.println(">>>>" + "NO ESTA");
        return false;
    }

    public static float distFrom(LatLng p1, LatLng p2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(p2.latitude - p1.latitude);
        double dLng = Math.toRadians(p2.longitude - p1.longitude );
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(p1.latitude)) * Math.cos(Math.toRadians(p2.latitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        return dist;
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


}

