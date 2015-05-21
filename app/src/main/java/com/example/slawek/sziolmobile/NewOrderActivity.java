package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Models.Client;
import Orders.NewOrder;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class NewOrderActivity extends Activity {

    int statusik;
    Client cl;
    Order ord;
    EditText title, description, status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_order);

        title = (EditText)findViewById(R.id.ET_order_title);
        description = (EditText)findViewById(R.id.ET_order_description);
        status = (EditText)findViewById(R.id.ET_order_status);
        cl = ClientsActivitySettings.getSingleClient();
    }

    public void addNewOrderActivityOnClick(View v)
    {
        /// tuuuuuuuuuuuuuu

        ord = new Order(title.getText().toString(), description.getText().toString(), "C", Integer.parseInt(cl.getId()));

        //luka w bazie

    //    ord = new Order("tytuł zlecenia", "opis jakis tam lalala", "C", 15);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                statusik = restService.AddNewOrder(ord);
            }
        });

        if(statusik == 200)
        {
    //        Toast.makeText(getApplicationContext(), "dodano ok", Toast.LENGTH_LONG).show();
        }
        else
        {
    //        Toast.makeText(getApplicationContext(), "NIE dodano", Toast.LENGTH_LONG).show();
        }



        Intent myIntent = new Intent(v.getContext(), MainMenu.class);
        NewOrderActivity.this.startActivity(myIntent);
        finish();
    }
}
