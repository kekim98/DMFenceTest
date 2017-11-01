package com.example.bawoori.dmfencetest;

import android.app.Application;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by bawoori on 17. 10. 31.
 */

public class DMTestApplication extends Application {

    private static final String TAG =  DMTestApplication.class.getSimpleName();
    //private FusedLocationProviderClient mFusedLocationClient;
    private static LocationManager locationManager = null;

    private Location mLastLocation;

    @Override
    public void onCreate() {
        super.onCreate();

        /*mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    mLastLocation = task.getResult();

                           *//* mLatitudeText.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    mLatitudeLabel,
                                    mLastLocation.getLatitude()));
                            mLongitudeText.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    mLongitudeLabel,
                                    mLastLocation.getLongitude()));*//*
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.getException());
                }
            }
        });
        mFusedLocationClient.requestLocationUpdates()*/

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLastLocation = location;

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d(TAG, "onStatusChanged.provider: " + provider + " ,status:"+status);
            }
            public void onProviderEnabled(String provider) {
                Log.d(TAG, "onProviderEnabled.provider: " + provider + " enabled");
            }
            public void onProviderDisabled(String provider) {
                Log.d(TAG, "onProviderDisabled.provider: " + provider + " disabled");
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);



    }


    public Double getLatitude() {
        if (mLastLocation != null) {
            return mLastLocation.getLatitude();
        }
        return 0.0;
    }

    public Double getLongitude() {
        if (mLastLocation != null) {
            return mLastLocation.getLongitude();
        }
        return 0.0;
    }

}
