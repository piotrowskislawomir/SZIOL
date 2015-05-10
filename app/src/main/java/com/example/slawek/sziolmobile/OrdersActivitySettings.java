package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class OrdersActivitySettings extends Activity {
    EditText et;

    public static Order or;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_options);

        or = OrdersActivity.or;


        et = (EditText) findViewById(R.id.ET_orders_settings);
        et.append(OrdersActivity.or.getTitle() + " " + OrdersActivity.or.getDescription() + "\n"
                + OrdersActivity.or.getStatus() + " " + OrdersActivity.or.getCustomerId());
    }

    public void editOrderOnClick(View v) {
        Intent myIntent = new Intent(v.getContext(), EditOrderActivity.class);
        OrdersActivitySettings.this.startActivity(myIntent);
        finish();


    }

    public void deleteOrderOnClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
              //  restService.DeleteOrder(OrdersActivity.or.getId());


                //    TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });


        Intent myIntent = new Intent(v.getContext(), MainMenu.class);
        OrdersActivitySettings.this.startActivity(myIntent);
        finish();


    }

}
