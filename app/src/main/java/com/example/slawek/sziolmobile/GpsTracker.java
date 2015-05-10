package com.example.slawek.sziolmobile;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Michał on 2015-05-10.
 */
public class GpsTracker {

    private Context context = null;
    boolean gpsEnabled = false;
    boolean networkEnabled = false;

    private static final long MIN_DISTANCE_CHANGE_TO_UPDATE = 1;
    private static final long MIN_TIME_TO_UPDATE = 1000 * 30 * 1;

    private LocationManager locationManager;

    public GpsTracker(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }

    public void getLocation() {
        try {
            GpsLocalizator gpsLocalizator = new GpsLocalizator();
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!gpsEnabled && !networkEnabled) {
            } else {
                if (networkEnabled && !gpsEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_TO_UPDATE, MIN_DISTANCE_CHANGE_TO_UPDATE, gpsLocalizator);
                }

                if (gpsEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_TO_UPDATE, MIN_DISTANCE_CHANGE_TO_UPDATE, gpsLocalizator);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean IsProviderChanged() {
        boolean gpsEnabledNew = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabledNew = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return gpsEnabled == !gpsEnabledNew || networkEnabled != networkEnabledNew;
    }
}
