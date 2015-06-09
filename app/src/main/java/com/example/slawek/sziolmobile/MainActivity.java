package com.example.slawek.sziolmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import sziolmobile.RestClientService;
import sziolmobile.RestService;


public class MainActivity extends ActionBarActivity {


    private SharedPropertiesManager sharedPropertiesManager;
    private Resources resources;

    public void startInternetService()
    {
        Intent serviceIntent = new Intent(this, InternetConnectionService.class);
        startService(serviceIntent);
    }

    public void stopInternetService() {
        Intent serviceIntent = new Intent(this, InternetConnectionService.class);
        stopService(serviceIntent);
    }

    public void startMyService() {
        Intent serviceIntent = new Intent(this, GpsService.class);
        startService(serviceIntent);
    }

    public void stopMyService() {
        Intent serviceIntent = new Intent(this, GpsService.class);
        stopService(serviceIntent);
    }

    public void startNotificationService() {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);
    }

    public void stopNotificationService() {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        stopService(serviceIntent);
    }

    /*
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }*/


 @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);

     Thread.setDefaultUncaughtExceptionHandler(
             new Thread.UncaughtExceptionHandler() {

                 @Override
                 public void uncaughtException(Thread thread, Throwable ex) {
                     Log.e("Error", "Unhandled exception: " + ex.getMessage());

                     ExceptionLogger exceptionLogger = new ExceptionLogger();
                     exceptionLogger.writefile("sziolerror.txt", ex.getMessage());
                     System.exit(1);
                 }
             });

     sharedPropertiesManager = new SharedPropertiesManager(getApplicationContext());
     resources =getApplicationContext().getResources();
   /*  if(!isOnline())
     {
          Alert("BRAK POŁĄCZENIA Z INTERNETEM");
     }
     else
     {*/
         try {
             stopNotificationService();
         } catch (Exception ex) {}
           startNotificationService();

       /*  try {
             stopInternetService();
         } catch (Exception ex) {}
         startInternetService();
*/
         try {
             stopMyService();
         } catch (Exception ex) {}
           startMyService();


         String token = sharedPropertiesManager.GetValue(resources.getString(R.string.shared_token), null);
         //if(InternetConnectionService.getLoginStatus())

        if(token != null)
         {
             Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
             //        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             //           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(intent);     //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

         }
     //}

     //
   //  Intent myIntent = new Intent(MainActivity.this, NavigationActivity.class);
   //  MainActivity.this.startActivity(myIntent);
     //
}

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
  }


    // przycisk logowanie
    public void buttonLogOnClick(View v)
    {
         Intent myIntent = new Intent(MainActivity.this, UserLog.class);
        MainActivity.this.startActivity(myIntent);
    }

    // przycisk rejestracja
    public void buttonRegOnClick(View v)
    {
        //  startActivity(new Intent("com.example.slawek.sziolmobile.UserLog"));
        Intent myIntent = new Intent(MainActivity.this, UserReg.class);
        MainActivity.this.startActivity(myIntent);
    }



}
