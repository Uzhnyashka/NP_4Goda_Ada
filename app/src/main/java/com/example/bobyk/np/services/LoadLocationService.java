package com.example.bobyk.np.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.bobyk.np.global.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.Calendar;

/**
 * Created by bobyk on 6/12/17.
 */

public class LoadLocationService extends Service {

    public static final String REQUEST_CHECK_SETTINGS = "REQUEST_CHECK_SETTINGS";
    private static final long LOCATION_REQUEST_INTERVAL = 1000 * 5;
    private static final String TAG = LoadLocationService.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private Location mLocation;
    private GoogleApiClient.ConnectionCallbacks connectionCallback;
    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener;
    private LocationListener locationListener;
    private LocationRequest locationRequest;
    private PendingResult<LocationSettingsResult> result;
    private boolean isRequestingUpdates;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println(TAG + " onCreate");
        initListeners();
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(connectionCallback)
                    .addOnConnectionFailedListener(connectionFailedListener)
                    .build();
        }
    }

    private void initListeners() {
        connectionCallback = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                Log.d(TAG, "onConnected: ");
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    locationRequest = new LocationRequest();
                    locationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
                    locationRequest.setFastestInterval(LOCATION_REQUEST_INTERVAL * 2);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                            builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                        @Override
                        public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                            Log.d(TAG, "onResult: locationResult" + locationSettingsResult.toString());
                            final Status status = locationSettingsResult.getStatus();
                            final LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied. The client can
                                    // initialize location requests here.
                                    Log.d(TAG, "onResult: SUCCESS");
                                    requestLocationUpdates();

                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    Log.d(TAG, "onResult: RESOLUTION_REQUIRED");
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. However, we have no way
                                    // to fix the settings so we won't show the dialog.
                                    Log.d(TAG, "onResult: SETTINGS_CHANGE_UNAVAILABLE");
                                    break;
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "onConnected: no permission");
                }
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        };

        connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
            }
        };

        locationListener = new LocationListener() {
            public void onLocationChanged(final Location newLocation) {
                // Called when a new location is found by the network location provider.
                System.out.println(TAG + " " + newLocation.getLatitude() + " " + newLocation.getLongitude());
            }
        };
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        connectClient();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnectClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getDriverLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getDriverLocation() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, locationListener);
        isRequestingUpdates = true;
    }


    private void connectClient() {
        Log.d(TAG, "connectClient: isConnected: " + googleApiClient.isConnected() + " isConnecting: " + googleApiClient.isConnecting());
        if (!googleApiClient.isConnected() && !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    private void disconnectClient() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                stopLocationUpdates();
                googleApiClient.disconnect();
            }
        }
    }

    private void stopLocationUpdates() {
        if (isRequestingUpdates) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, locationListener);
            Log.d(TAG, "stopLocationUpdates: ");
        }
    }

}
