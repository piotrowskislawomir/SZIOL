package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log);

        log = (EditText)findViewById(R.id.ET_log_log);
        pass = (EditText)findViewById(R.id.ET_pass_log);


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
                restService.SendClientLogin(log.getText().toString(), pass.getText().toString());
                //restService.GetClientById(2);

                TextView tv = (TextView)findViewById(R.id.textView3);

                try {
                    JSONObject jsonObj = new JSONObject(RestClientService.resp);
                    res = jsonObj.get("Result").toString();
                    token = jsonObj.get("Token").toString();
                 //   tv.setText(token);
                 }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
                if(res == "true" && token != "") {
             //        Intent myIntent = new Intent(UserLog.this, MainActivity.class);
              //       UserLog.this.startActivity(myIntent);
               //      UserLog.this.onStop();

                    Intent intent = new Intent(UserLog.this, MainMenu.class);
                 //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                }
        });


    }
    }


