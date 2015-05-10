package com.example.slawek.sziolmobile;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;

import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-09.
 */
public class GpsLocalizator implements LocationListener {



    @Override
    public void onLocationChanged(Location location) {

        Coordinate cor = new Coordinate(location.getLatitude(), location.getLongitude());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
    //    restClientService.SetToken(UserLog.token);
                RestService restService = new RestService(restClientService);
                restService.SendLocation(cor);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
