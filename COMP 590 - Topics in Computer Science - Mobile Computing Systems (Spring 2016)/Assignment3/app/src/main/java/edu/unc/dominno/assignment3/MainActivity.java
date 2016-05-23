package edu.unc.dominno.assignment3;

import android.Manifest;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback,
        MediaPlayer.OnPreparedListener/*, ResultCallback<Status>*/ {
    private GoogleApiClient c = null;
    MediaPlayer mp = null;
    /*ArrayList<Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;
    private boolean mGeofencesAdded;
    protected static final String TAG = "MainActivity";*/
    private String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (c == null) {
            c = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        GoogleMapOptions options = new GoogleMapOptions();
        CameraPosition pos = new CameraPosition(new LatLng(35.910812, -79.051969), 16, 0, 0);

        options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .liteMode(true)
                .camera(pos)
                .rotateGesturesEnabled(false)
                .scrollGesturesEnabled(false)
                .zoomControlsEnabled(false)
                .zoomGesturesEnabled(false)
                .tiltGesturesEnabled(false);

        MapFragment fixedMap = MapFragment.newInstance(options);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapHolder, fixedMap);
        fragmentTransaction.commit();

        if (fixedMap != null) {
            fixedMap.getMapAsync(this);
        }

        place = "none";

        /*mGeofenceList = new ArrayList<Geofence>();

        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("Brooks Entrance")
                .setCircularRegion(35.909647, -79.053028, 100)
                .setExpirationDuration(Long.MAX_VALUE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("Polk Place")
                .setCircularRegion(35.910821, -79.050448, 100)
                .setExpirationDuration(Long.MAX_VALUE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("Old Well")
                .setCircularRegion(35.912007, -79.051170, 100)
                .setExpirationDuration(Long.MAX_VALUE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());


        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.GeofencingApi.addGeofences(
                    c,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this);
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }*/

    }

    /*private void logSecurityException(SecurityException securityException) {
        Log.e(TAG, "Invalid location permission. " +
                "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            // Update state and save in shared preferences.
            mGeofencesAdded = !mGeofencesAdded;
            *//*SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(SyncStateContract.Constants.GEOFENCES_ADDED_KEY, mGeofencesAdded);
            editor.apply();

            // Update the UI. Adding geofences enables the Remove Geofences button, and removing
            // geofences enables the Add Geofences button.
            setButtonsEnabledState();*//*

            Toast.makeText(
                    this,
                    getString(mGeofencesAdded ? R.string.geofences_added :
                            R.string.geofences_removed),
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    status.getStatusCode());
            Log.e(TAG, errorMessage);
        }
    }*/

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("TAG", "We are connected to Google Services");
        try {
            Location loc = LocationServices.FusedLocationApi.getLastLocation(c);
            Log.v("LOC", "" + loc.getLatitude() + ", " + loc.getLongitude());

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(500);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.FusedLocationApi.requestLocationUpdates(c, mLocationRequest, this);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        c.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        c.disconnect();
        super.onStop();

        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("Coordinates", location.getLatitude() + ", " + location.getLongitude());

        Geocoder g = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> la = g.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Log.v("Address", la.get(0).toString());
            TextView t1 = (TextView)findViewById(R.id.coord);
            t1.setText("Current Coordinates: " + "\n" + location.getLatitude() + ", " + location.getLongitude());

            TextView t2 = (TextView)findViewById(R.id.addr);
            t2.setText("Current Address: "
                    + "\n" + la.get(0).getAddressLine(0)
                    + "\n" + la.get(0).getLocality()
                    + ", " + la.get(0).getAdminArea()
                    + " " + la.get(0).getPostalCode());

            /*if (la.get(0).getAddressLine(0).equals("201 S Columbia St")) {

            } else if (la.get(0).getAddressLine(0).equals("180 E Cameron Ave")) {

            } else if (la.get(0).getAddressLine(0).equals("250 E Cameron Ave")) {

            } else {

            }*/

            if ((location.getLatitude() > 35.909200 && location.getLatitude() < 35.910500)
                    && (location.getLongitude() > -79.053900 && location.getLongitude() < -79.052375)) {
                ImageView i = (ImageView)findViewById(R.id.circle);
                i.setBackgroundResource(R.drawable.red);

                if (mp != null) {
                    if (mp.isPlaying() && place.equals("Sitterson")) {
                        // Keep playing, so do nothing
                    } else if (mp.isPlaying() && !place.equals("Sitterson")) {
                        // Stop playing, change song to Cool Kids
                        place = "Sitterson";
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(this, R.raw.kids);
                        mp.start();
                    } else if (!mp.isPlaying() && place.equals("Sitterson")) {
                        // Resume
                        mp.start();
                    } else if (!mp.isPlaying() && !place.equals("Sitterson")) {
                        // Release old mp, change song to Cool Kids
                        place = "Sitterson";
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(this, R.raw.kids);
                        mp.start();
                    }
                } else {
                    place = "Sitterson";
                    mp = MediaPlayer.create(this, R.raw.kids);
                    mp.start();
                }
            } else if ((location.getLatitude() > 35.909700 && location.getLatitude() < 35.911550)
                    && (location.getLongitude() > -79.051200 && location.getLongitude() < -79.049600)) {
                ImageView i = (ImageView)findViewById(R.id.circle);
                i.setBackgroundResource(R.drawable.red);

                if (mp != null) {
                    if (mp.isPlaying() && place.equals("Polk Place")) {
                        // Keep playing, so do nothing
                    } else if (mp.isPlaying() && !place.equals("Polk Place")) {
                        // Stop playing, change song to sunshine
                        place = "Polk Place";
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(this, R.raw.sunshine);
                        mp.start();
                    } else if (!mp.isPlaying() && place.equals("Polk Place")) {
                        // Resume
                        mp.start();
                    } else if (!mp.isPlaying() && !place.equals("Polk Place")) {
                        // Release old mp, change song to sunshine
                        place = "Polk Place";
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(this, R.raw.sunshine);
                        mp.start();
                    }
                } else {
                    place = "Polk Place";
                    mp = MediaPlayer.create(this, R.raw.sunshine);
                    mp.start();
                }


            } else if ((location.getLatitude() > 35.911750 && location.getLatitude() < 35.912400)
                    && (location.getLongitude() > -79.051750 && location.getLongitude() < -79.050650)) {
                ImageView i = (ImageView)findViewById(R.id.circle);
                i.setBackgroundResource(R.drawable.red);

                if (mp != null) {
                    if (mp.isPlaying() && place.equals("Old Well")) {
                        // Keep playing, so do nothing
                    } else if (mp.isPlaying() && !place.equals("Old Well")) {
                        // Stop playing, change song to Sugar
                        place = "Old Well";
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(this, R.raw.sugar);
                        mp.start();
                    } else if (!mp.isPlaying() && place.equals("Old Well")) {
                        // Resume
                        mp.start();
                    } else if (!mp.isPlaying() && !place.equals("Old Well")) {
                        // Release old mp, change song to Sugar
                        place = "Old Well";
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(this, R.raw.sugar);
                        mp.start();
                    }
                } else {
                    place = "Old Well";
                    mp = MediaPlayer.create(this, R.raw.sugar);
                    mp.start();
                }
            } else {
                ImageView i = (ImageView)findViewById(R.id.circle);
                i.setBackgroundResource(R.drawable.green);

                if (mp != null) {
                    if (mp.isPlaying()) {
                        // Pause
                        mp.pause();
                    } else {
                        // Keep paused, so do nothing
                    }
                } else {
                    // Keep mp = null
                }
            }

        } catch (Exception ex) {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(35.909647,-79.053028)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(35.912007,-79.051170)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(35.910821,-79.050448)));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.v("MP", "onPrepared called");
        if (mp != null) {
            mp.start();
        }
    }
}
