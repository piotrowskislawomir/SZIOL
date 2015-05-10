package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class ClientsActivitySettings extends Activity {

    EditText et;

    public static Client  cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_options);

        cl = ClientsActivity.cl;

        et = (EditText) findViewById(R.id.ET_clients_settings);
        et.append(ClientsActivity.cl.getFirstName() + " " + ClientsActivity.cl.getLastName() + "\n"
                + ClientsActivity.cl.getStreet() + " " + ClientsActivity.cl.getHomeNumber() + " " + ClientsActivity.cl.getFlatNumber() + "\n" +
                ClientsActivity.cl.getCity());
    }

    public void editClientOnClick(View v) {
        Intent myIntent = new Intent(v.getContext(), EditClientActivity.class);
        ClientsActivitySettings.this.startActivity(myIntent);
        finish();


    }

    public void deleteClientOnClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.DeleteCustomer(ClientsActivity.cl.getId());


            //    TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });


        Intent myIntent = new Intent(v.getContext(), MainMenu.class);
        ClientsActivitySettings.this.startActivity(myIntent);
        finish();


    }

    public void addNewOrderOnClick(View v)
    {
        Intent myIntent = new Intent(v.getContext(), NewOrderActivity.class);
        ClientsActivitySettings.this.startActivity(myIntent);
        finish();
    }
}
