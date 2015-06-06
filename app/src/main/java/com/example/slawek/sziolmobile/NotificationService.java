package com.example.slawek.sziolmobile;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
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

    String token;
    String login;
    String password;

    private SharedPropertiesManager sharedPropertiesManager;
    private Resources resources;
    RestClientService restClientService;
    RestService restService;


    public NotificationService()
    {
        Initialize();
    }

    private void Initialize()
    {
        restClientService  = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        restService = new RestService(restClientService);
    }

    public static JSONArray notifications;

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {

            token = sharedPropertiesManager.GetValue(resources.getString(R.string.shared_token), null);

            if(token != null)
            {
                int status = restService.GetNotifications();

                if(status != 200)
                {
                    Login();
                    restService.GetNotifications();
                }

                try {
                    notifications = new JSONArray(RestClientService.resp);
                    ProcessNotifications();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
         }
    }

    private void ProcessNotifications() {
        String id;
        String title;
        String description;
        String ticketId;
        String type;

        for (int i = 0; i < notifications.length(); i++) {
            try {
                JSONObject jsonObj = notifications.getJSONObject(i);
                id = jsonObj.get("Id").toString();
                title = jsonObj.get("Title").toString();
                description = jsonObj.get("Description").toString();
                ticketId = jsonObj.get("TicketId").toString();
                type = jsonObj.get("Type").toString();
                NotificationModel notification = new NotificationModel(id, title, description, ticketId, type);
                restService.DeleteNotification(Integer.parseInt(id));

                Boolean notificationEnable = Boolean.parseBoolean(sharedPropertiesManager.GetValue(resources.getString(R.string.shared_notification_enable), "true"));

                if(notificationEnable) {


                    if(type.equals("NW"))
                    {
                        NotifyNearestWorker(notification);
                    }
                    else if(type.equals("CE"))
                    {
                        NotifyChangedExecutor(notification);
                    }
                    else if(type.equals("DT"))
                    {
                        NotifyDeletedTicket(notification);
                    }
                }
                else
                {
                    if(type.equals("NW")) {
                        restService.SendStatusNotification(Integer.parseInt(notification.getId()), notification.getTicketId(), false);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void NotifyNearestWorker(NotificationModel notificationModel)
    {
        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        Notification note=new Notification(R.drawable.abc_ab_share_pack_holo_dark,
                notificationModel.getTitle(),
                System.currentTimeMillis());
        Intent notificationIntent = new Intent(getBaseContext(), NotificationReciver.class);
        notificationIntent.putExtra("notification", notificationModel);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // This pending intent will open after notification click
        PendingIntent i=PendingIntent.getActivity(this, 0,
                notificationIntent,
                0);


        note.setLatestEventInfo(this, notificationModel.getTitle(),
                notificationModel.getDescription(),  i);

        mgr.notify(2, note);
    }


    private void NotifyDeletedTicket(NotificationModel notificationModel)
    {
        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        Notification note=new Notification(R.drawable.abc_ab_share_pack_holo_dark,
                notificationModel.getTitle(),
                System.currentTimeMillis());
        note.setLatestEventInfo(this, notificationModel.getTitle(),
                notificationModel.getDescription(),  null);

        mgr.notify(2, note);
    }

    private void NotifyChangedExecutor(NotificationModel notificationModel)
    {
        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        Notification note=new Notification(R.drawable.abc_ab_share_pack_holo_dark,
                notificationModel.getTitle(),
                System.currentTimeMillis());
        Intent notificationIntent = new Intent(getBaseContext(), OrdersActivitySettings.class);
          notificationIntent.putExtra("notification", notificationModel);
          notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // This pending intent will open after notification click
         PendingIntent i=PendingIntent.getActivity(this, 0,
                 notificationIntent,
                 0);

        note.setLatestEventInfo(this, notificationModel.getTitle(),
                notificationModel.getDescription(),  i);

        mgr.notify(2, note);
    }


    private boolean Login()
    {
        login = sharedPropertiesManager.GetValue(resources.getString(R.string.shared_login), null);
        password = sharedPropertiesManager.GetValue(resources.getString(R.string.shared_password), null);

        if(login != null && password != null)
        {
            int status = restService.SendClientLogin(login, password);

            try
            {
                if(status == 200)
                {
                    JSONObject jsonObj = new JSONObject(RestClientService.resp);
                    String result = jsonObj.get("Result").toString();
                    String token = jsonObj.get("Token").toString();

                    if(result == "true" && token != null)
                    {
                        RestClientService.SetToken(token);
                        return  true;
                    }
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        return false;
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

        sharedPropertiesManager = new SharedPropertiesManager(getApplicationContext());
        resources =getApplicationContext().getResources();


        writeToLogs("Called onStartCommand() methond");
        clearTimerSchedule();
        initTask();
        timer.scheduleAtFixedRate(timerTask,  1000, 1 * 60 * 1000);
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
