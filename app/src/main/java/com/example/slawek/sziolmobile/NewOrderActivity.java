package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

import Models.Client;
import Orders.NewOrder;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class NewOrderActivity extends Activity {

    Client cl;
    Order ord;
    EditText title, description, status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);

        title = (EditText)findViewById(R.id.ET_order_title);
        description = (EditText)findViewById(R.id.ET_order_description);
        status = (EditText)findViewById(R.id.ET_order_status);
        cl = ClientsActivitySettings.cl;


    }

    public void addNewOrderActivityOnClick(View v)
    {
        ord = new Order(title.getText().toString(), description.getText().toString(), "C", Integer.parseInt(cl.getId()));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.AddNewOrder(ord);
            }
        });


        Intent myIntent = new Intent(v.getContext(), MainMenu.class);
        NewOrderActivity.this.startActivity(myIntent);
        finish();
    }
}
