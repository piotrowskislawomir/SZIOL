package com.example.slawek.sziolmobile;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import java.util.Timer;

/**
 * Created by Michał on 2015-05-10.
 */
public class GpsService extends Service {

        private GpsTracker gpsTracker;
        private Timer timer;
        private TimerTask timerTask;

        private class MyTimerTask extends TimerTask {
            @Override
            public void run() {
       /*         boolean isChanged = gpsTracker.IsProviderChanged();
                if(isChanged)
                {
                    gpsTracker.getLocation();
                }*/
            }

        }

        private void writeToLogs(String message) {
            Log.d("GpsService", message);
        }

        @Override
        public void onCreate() {
            super.onCreate();
            writeToLogs("Called onCreate() method.");
            gpsTracker=new GpsTracker(getApplicationContext());

            timer = new Timer();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            writeToLogs("Called onStartCommand() methond");
            clearTimerSchedule();
            initTask();
            gpsTracker.getLocation();
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
            writeToLogs("Called onDestroy() method");
            clearTimerSchedule();
            super.onDestroy();
        }

        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }
    }
