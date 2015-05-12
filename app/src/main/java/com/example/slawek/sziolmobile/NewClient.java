package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-04-12.
 */
public class NewClient extends Activity {

    private EditText firstNameClient;
    private EditText lastNameClient;
   // private EditText addressClient;
    private EditText streetClient;
    private EditText homeNumberClient;
    private EditText flatNumberClient;
    private EditText cityClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_client);
        firstNameClient = (EditText)findViewById(R.id.ET_new_client_name);
        lastNameClient = (EditText)findViewById(R.id.ET_new_client_last_name);
      //  addressClient = (EditText)findViewById(R.id.ET_new_client_address);
        cityClient = (EditText)findViewById(R.id.ET_new_client_city);
        streetClient = (EditText)findViewById(R.id.ET_new_client_street);
        homeNumberClient = (EditText)findViewById(R.id.ET_client_home_number);
        flatNumberClient = (EditText)findViewById(R.id.ET_new_client_flat_number);
       // this.city = city;
       // this.street = street;
       // this.homeNumber = homeNumber;
       // this.flatNumber = flatNumber;
    }


    public void addNewClientButtonOnClick(View v) {


    final Client client = new Client (firstNameClient.getText().toString(),lastNameClient.getText().toString(), cityClient.getText().toString(),  streetClient.getText().toString(), homeNumberClient.getText().toString(), flatNumberClient.getText().toString());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
              restService.AddNewCustomer(client);
              // restService.DeleteCustomer(8);

                TextView tv = (TextView) findViewById(R.id.textView3);

        }
        });

       Intent intent = new Intent(NewClient.this, MainMenu.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}