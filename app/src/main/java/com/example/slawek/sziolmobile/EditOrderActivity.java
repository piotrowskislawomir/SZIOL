package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class EditOrderActivity extends Activity {
    EditText title, desc, status; /// i tutaj inne, home, street, city;

    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        bt = (Button)findViewById(R.id.BT_edit_order);

        title = (EditText)findViewById(R.id.ET_order_title);
        desc = (EditText)findViewById(R.id.ET_edit_order_desc);
        status = (EditText)findViewById(R.id.ET_order_status);



        title.setText(OrdersActivitySettings.or.getTitle().toString());
        desc.setText(OrdersActivitySettings.or.getDescription().toString());
        status.setText(OrdersActivitySettings.or.getStatus().toString());

        //      flat.setText(ClientsActivitySettings.cl.getFlatNumber().toString());
        //     street.setText(ClientsActivitySettings.cl.getStreet().toString());
        //     home.setText(ClientsActivitySettings.cl.getHomeNumber().toString());
        //     city.setText(ClientsActivitySettings.cl.getCity().toString());


        // Client client = new Client(ClientsActivitySettings.cl.getId());

    }

    public void saveEditOrderOnClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                   // tu zmiana     restService.EditOrder(ClientsActivitySettings.cl.getId(), );


                TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });

        //Intent intent = new Intent(NewClient.this, MainMenu.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        //finish();



        // Intent myIntent = new Intent(v.getContext(), MainMenu.class);
        //  ClientsActivitySettings.this.startActivityForResult(myIntent, 0);




    }
}
