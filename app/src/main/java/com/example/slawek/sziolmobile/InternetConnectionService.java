package com.example.slawek.sziolmobile;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class InternetConnectionService extends Service {
    private Timer timer;
    private TimerTask timerTask;

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
            if(!isOnline())
            {
                    Toast.makeText(getApplicationContext(), "Problem połączenia z internetem", Toast.LENGTH_LONG).show();
            }
                else
            {
                Toast.makeText(getApplicationContext(), "Połaczenie ok", Toast.LENGTH_LONG).show();

            }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

      @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        clearTimerSchedule();
        initTask();
        timer.scheduleAtFixedRate(timerTask,  1000, 2 * 10 * 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    private void clearTimerSchedule() {
        if(timerTask != null) {
            timerTask.cancel();
            timer.purge();
        }
    }

    private void initTask() {
        timerTask = new MyTimerTask();
    }

    @Override
    public void onDestroy() {
        clearTimerSchedule();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


}
