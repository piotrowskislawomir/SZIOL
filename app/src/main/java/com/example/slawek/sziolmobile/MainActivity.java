package com.example.slawek.sziolmobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import sziolmobile.RestClientService;
import sziolmobile.RestService;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    Timer timer;
    TimerTask timerTask;
    GpsLocalizator gps;

    final Handler handler = new Handler();

 //   @Override
  //  protected void onResume() {
   //     super.onResume();

     //   startTimer();
    //}

    public void startTimer() {
        timer = new Timer();
        //initializeTimerTask();
        timer.schedule(timerTask, 5000, 10000); //
    }

    public void stoptimertask(View v) {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /*
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp

                        getLocation();
                    }
                });
            }
        };
    }
*/

private void Alert()
{
    new AlertDialog.Builder(this)
            .setTitle("Zadanie")
            .setMessage("Czy przyjmujesz zadanie?")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
                    // .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
}

    private void startMyService() {
        Intent serviceIntent = new Intent(this, GpsService.class);
        startService(serviceIntent);
    }

    private void stopMyService() {
        Intent serviceIntent = new Intent(this, GpsService.class);
        stopService(serviceIntent);
    }

    private void startNotificationService() {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);
    }

    private void stopNotificationService() {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        stopService(serviceIntent);
    }


 @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);

     //  startTimer();

     try
     {
         stopNotificationService();
     }
     catch(Exception ex){}
    startNotificationService();
    // Alert();   // startGpsService();

     ///////////////////





/*
   gps = new GpsTracker(MainActivity.this);

        if(gps.canGetLocation())
        {
            double latitude = gps.getLatitude();
            double longitiude = gps.getLongitude();
           Toast.makeText(getBaseContext(), "Twoja lokalizacja to:" + latitude + " " + longitiude, Toast.LENGTH_LONG).show();
       }
      else
      {
          gps.showSettingAlert();
      }
*/
     try
     {
     stopMyService();}
     catch(Exception ex)
     {}
    startMyService();
 }

    //////





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
      //      Intent intent = new Intent(this, NotificationActivity.class);
      //      startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonClicked(View v)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
     //           restService.SendLocation(1, "12.232323", "12.42332");
            }
        });
    }

    // przycisk logowanie
    public void buttonLogOnClick(View v)
    {
     //   gps = new GpsTracker(MainActivity.this);

     //   if(gps.canGetLocation())
     //   {
     //       double latitude = gps.getLatitude();
     //       double longitiude = gps.getLongitude();
      //      Toast.makeText(getApplicationContext(), "Twoja lokalizacja to:" + latitude + " " + longitiude, Toast.LENGTH_LONG).show();
      //  }
       // else
       // {
       //     gps.showSettingAlert();
       // }

        //
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
