package com.example.slawek.sziolmobile;

//
//package com.example.slawek.myapplication;

        import android.app.AlertDialog;
        import android.content.BroadcastReceiver;
        import android.content.ComponentName;
        import android.content.ContentResolver;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.content.IntentSender;
        import android.content.ServiceConnection;
        import android.content.SharedPreferences;
        import android.content.pm.ApplicationInfo;
        import android.content.pm.PackageManager;
        import android.content.res.AssetManager;
        import android.content.res.Configuration;
        import android.content.res.Resources;
        import android.database.DatabaseErrorHandler;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Bitmap;
        import android.graphics.drawable.Drawable;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.net.Uri;
        import android.os.Handler;
        import android.os.Looper;
        import android.os.StrictMode;
        import android.os.UserHandle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.view.Display;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.TextView;

        import org.apache.http.HttpRequest;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.ResponseHandler;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.BasicResponseHandler;
        import org.apache.http.impl.client.DefaultHttpClient;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.util.List;
        import java.util.Locale;
        import java.util.Timer;
//




/**
 * Created by Michał on 2015-05-09.
 */
public class Coordinate {
    double latitude;
    double longitude;
    String nameCity;

    public Coordinate(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinate(double latitude, double longitude, String nameCity)
    {
        this.nameCity = nameCity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){ return latitude; }
    public double getLongitude(){ return longitude; }
    public String toString() {
        return this.nameCity;
    }



    //////



    }


