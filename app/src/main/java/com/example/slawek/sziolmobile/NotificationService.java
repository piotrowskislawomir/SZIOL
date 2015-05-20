package com.example.slawek.sziolmobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import Models.NotificationModel;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class NotificationService extends Service {
    private Timer timer;
    private TimerTask timerTask;

    private static NotificationModel notification;

    public static NotificationModel getNotification()
    {
       return notification;
    }

    public static JSONArray notifications;
    RestService restService;
    public NotificationService()
    {
        RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        restService = new RestService(restClientService);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {

            restService.GetNotifications();
            try {
                notifications = new JSONArray(RestClientService.resp);
                ProcessNotifications();

            } catch (JSONException e) {
                e.printStackTrace();
            }
         }

    }

    private void ProcessNotifications() {
        String id;
        String title;
        String description;
        String ticketId;

        for (int i = 0; i < notifications.length(); i++) {
            try {
                JSONObject jsonObj = notifications.getJSONObject(i);
                id = jsonObj.get("Id").toString();
                title = jsonObj.get("Title").toString();
                description = jsonObj.get("Description").toString();
                ticketId = jsonObj.get("TicketId").toString();


                //
                notification = new NotificationModel(id, title, description, ticketId);

                //
                Notify(title, description, ticketId);
                restService.DeleteNotification(Integer.parseInt(id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    private void Notify(String title, String description, String ticketId)
    {
        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        Notification note=new Notification(R.drawable.abc_ab_share_pack_holo_dark,
                title,
                System.currentTimeMillis());

        // This pending intent will open after notification click
        PendingIntent i=PendingIntent.getActivity(this, 0,
                new Intent(this, NotificationReciver.class),
                0);

        note.setLatestEventInfo(this, title,
                description + " : " + ticketId,  i);

        mgr.notify(2, note);
    }

    private void writeToLogs(String message) {
        Log.d("GpsService", message);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        writeToLogs("Called onStartCommand() methond");
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
        writeToLogs("Called onDestroy() method");
        clearTimerSchedule();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


}
