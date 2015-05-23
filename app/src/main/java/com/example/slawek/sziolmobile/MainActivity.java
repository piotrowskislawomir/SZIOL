package com.example.slawek.sziolmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    Timer timer;
    TimerTask timerTask;
    GpsNetLocalizator gps;

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

private void Alert(String str)
{
    new AlertDialog.Builder(this)
            .setTitle("Komunikat")
            .setMessage(str)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
           // .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
           //     public void onClick(DialogInterface dialog, int which) {
                    // do nothing
         //       }
           // })
                    // .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
}

    private void startInternetService() {
        Intent serviceIntent = new Intent(this, InternetConnectionService.class);
        startService(serviceIntent);
    }

    private void stopInternetService() {
        Intent serviceIntent = new Intent(this, InternetConnectionService.class);
        stopService(serviceIntent);
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


 @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);

     if(!isOnline())
     {
          Alert("BRAK POŁĄCZENIA Z INTERNETEM");
     }
     else
     {
         try {
             stopNotificationService();
         } catch (Exception ex) {}
           startNotificationService();

         try {
             stopInternetService();
         } catch (Exception ex) {}
         startInternetService();

         try {
             stopMyService();
         } catch (Exception ex) {}
           startMyService();
     }
}

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
  }

    /*
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
    */

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
