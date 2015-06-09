package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-04-12.
 */
public class MainMenu extends Activity{

   public static JSONArray clients;
    public static JSONArray orders;
    public static JSONArray cardsItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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

    public void clientMenuButtonOnClick(View v) {
            Intent intent = new Intent(MainMenu.this, ClientsActivity.class);
      //     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
      //  finish();
       //   Button btn = (Button)findViewById(R.id.button7);
       //  btn.setText(RestClientService.resp);
/*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.GetAllCustomers();
                try {
                    clients = new JSONArray(RestClientService.resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
  */
    }
    public void orderMenuButtonOnClick(View v)
    {
             Intent intent = new Intent(MainMenu.this, OrdersActivity.class);
     //          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
     //   finish();
     //      Button btn = (Button)findViewById(R.id.button7);
     //     btn.setText(RestClientService.resp);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                try
                {
                    orders = new JSONArray();
                    restService.GetAllOrders();
                try {
                    orders = new JSONArray(RestClientService.resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                }
            }
        });



    //    Intent intent = new Intent(MainMenu.this, OrdersActivity.class);
        //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     //   startActivity(intent);
       // finish();


    }

    public void myCardMenuButtonOnClick(View v)
    {

        //      Button btn = (Button)findViewById(R.id.button7);
        //     btn.setText(RestClientService.resp);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);

                try
                {
                    cardsItems = new JSONArray();
                    restService.GetMyCard();
                try {
                    cardsItems = new JSONArray(RestClientService.resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent intent = new Intent(MainMenu.this, CardItems.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
       // finish();


        //    Intent intent = new Intent(MainMenu.this, OrdersActivity.class);
        //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //   startActivity(intent);
        // finish();


    }

    public void teamOrders(View v)
    {
        Intent intent = new Intent(MainMenu.this, TeamOrders.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


    public void getClientsButtonOnClick(View v)
    {
      //  Intent intent = new Intent(MainMenu.this, NewClient.class);
        //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      //  startActivity(intent);
        //finish();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);

                try
                {
                    clients = new JSONArray();

                    restService.GetAllCustomers();
                    try {
                         clients = new JSONArray(RestClientService.resp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });




        Button btn = (Button)findViewById(R.id.button7);
        btn.setText(RestClientService.resp);


    }

}
