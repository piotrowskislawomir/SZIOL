package AndroidService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.slawek.sziolmobile.NotificationReciver;
import com.example.slawek.sziolmobile.OrdersActivitySettings;
import com.example.slawek.sziolmobile.R;
import com.example.slawek.sziolmobile.SharedPropertiesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import Models.NotificationModel;
import Services.ExceptionLogger;
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
            try {
                token = sharedPropertiesManager.GetValue(resources.getString(R.string.shared_token), null);

                if (token != null) {
                    int status = restService.GetNotifications();

                    if (status != 200) {
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
            catch (Exception ex)
            {
                ExceptionLogger exceptionLogger = new ExceptionLogger();
                exceptionLogger.writefile("sziolnotify.txt", ex.getMessage());
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
                        restService.SendStatusNotification(Integer.parseInt(notification.getId()), false);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    long[] pattern = {500,500,500,500,500,500,500,500};

    private void NotifyNearestWorker(NotificationModel notificationModel)
    {
        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(getBaseContext().NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(getBaseContext(), NotificationReciver.class);

        int uniqueNo =  (int)System.currentTimeMillis();

        notificationIntent.putExtra("notification", notificationModel);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.setAction(Integer.toString(uniqueNo));
        // This pending intent will open after notification click
        PendingIntent i=PendingIntent.getActivity(getBaseContext(),uniqueNo,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.abc_ab_share_pack_holo_dark)
                        .setContentTitle(notificationModel.getTitle())
                        .setContentText(notificationModel.getDescription());

        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(i);
        builder.setVibrate(pattern);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setBigContentTitle(notificationModel.getTitle());
        String[] lines = notificationModel.getDescription().split("\n");
        for(String line : lines)
        {
            style.addLine(line);
        }

        builder.setStyle(style);

        mgr.notify(uniqueNo, builder.build());
    }


    private void NotifyDeletedTicket(NotificationModel notificationModel)
    {
        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(getBaseContext().NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ab_share_pack_holo_dark)
                        .setContentTitle(notificationModel.getTitle())
                        .setContentText(notificationModel.getDescription());

        int uniqueNo =  (int)System.currentTimeMillis();

        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setVibrate(pattern);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setBigContentTitle(notificationModel.getTitle());
        String[] lines = notificationModel.getDescription().split("\n");
        for(String line : lines)
        {
            style.addLine(line);
        }
        builder.setStyle(style);

        mgr.notify(uniqueNo, builder.build());
    }

    private void NotifyChangedExecutor(NotificationModel notificationModel)
    {
        final NotificationManager mgr=
                (NotificationManager)this.getSystemService(getBaseContext().NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(getBaseContext(), OrdersActivitySettings.class);

        int uniqueNo =  (int)System.currentTimeMillis();

        notificationIntent.putExtra("notification", notificationModel);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.setAction(Integer.toString(uniqueNo));
        // This pending intent will open after notification click
        PendingIntent i=PendingIntent.getActivity(this,uniqueNo,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ab_share_pack_holo_dark)
                        .setContentTitle(notificationModel.getTitle())
                        .setContentText(notificationModel.getDescription());

        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(i);

        builder.setVibrate(pattern);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setBigContentTitle(notificationModel.getTitle());
        String[] lines = notificationModel.getDescription().split("\n");
        for(String line : lines)
        {
            style.addLine(line);
        }

        builder.setStyle(style);

        mgr.notify(uniqueNo, builder.build());
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


        writeToLogs("Called onStartCommand() method");
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
