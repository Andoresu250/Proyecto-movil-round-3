package com.example.dell.round3.Activity.Maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.SeekBar;

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

import java.io.IOException;


public class NewActivityMap extends MapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private GoogleApiClient mGoogleApiClient;
    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 1;
    LatLng latLng;
    GoogleMap mGoogleMap;
    public LatLng markLatLng;

    private void initListeners() {
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener( this );
        mGoogleMap.setOnMapClickListener(this);
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
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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
        mGoogleMap.setMyLocationEnabled(false);
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
        markLatLng = null;
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            initCamera(mLastLocation);
        }
    }

    public String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
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
    public void onMapClick(LatLng latLng) {

        markLatLng = latLng;
        SeekBar radiusSeekBar = (SeekBar) getActivity().findViewById(R.id.radiusSeekBar);
        radiusSeekBar.setEnabled(true);
        int radius = radiusSeekBar.getProgress();
        changeRadius(radius);

    }

    public void changeRadius(int radius){
        mGoogleMap.clear();
        MarkerOptions options = new MarkerOptions().position( markLatLng );
        options.icon( BitmapDescriptorFactory.defaultMarker() );
        mGoogleMap.addMarker( options );

        CircleOptions circleOptions = new CircleOptions()
                .center(markLatLng)   //set center
                .radius(radius)   //set radius in meters
                .fillColor(0x5558D3F7)
                .strokeColor(0x10000000)
                .strokeWidth(5);
        mGoogleMap.addCircle(circleOptions);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

}
