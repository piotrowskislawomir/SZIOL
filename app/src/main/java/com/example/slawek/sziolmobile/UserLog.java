package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slawek.sziolmobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import Models.User;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-04-11.
 */

    public class UserLog extends Activity {

    private EditText log;
    private EditText pass;
    String res = "";
    public static String token ="";

    // do sprawdzenia
    String message;

    ProgressDialog progress;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log);

        log = (EditText)findViewById(R.id.ET_log_log);
        pass = (EditText)findViewById(R.id.ET_pass_log);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);


        Notification n  = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setContentIntent(pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1, n);
        }


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
                return true;
            }

            return super.onOptionsItemSelected(item);
        }


        public void loginButtonOnClick(View v) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);

                if(restService.SendClientLogin(log.getText().toString(), pass.getText().toString()) == 200)
                {



                    try
                    {
                        // tu można wyciągnać status
                        JSONObject jsonObj = new JSONObject(RestClientService.resp);
                        res = jsonObj.get("Result").toString();
                        token = jsonObj.get("Token").toString();
                        message = jsonObj.get("Message").toString();

                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }

                    if(message != "null")
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    else
                    {
                        if(res == "true" && token != "")
                        {
                            Intent intent = new Intent(UserLog.this, MainMenu.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Błędny login lub hasło", Toast.LENGTH_SHORT).show();
                        }
                    }
                    }
                else
                {
                    Toast.makeText(getApplicationContext(), "Serwer nie odpowiedział", Toast.LENGTH_SHORT).show();
                   // progress = ProgressDialog.show(getApplicationContext(),"BŁĄD", "SERWER NIE ODPOWIADA" );
                }
            }

            });


    }

    }


