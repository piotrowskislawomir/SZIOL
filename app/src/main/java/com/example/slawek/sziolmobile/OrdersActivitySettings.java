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
import org.json.JSONObject;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class OrdersActivitySettings extends Activity {
    EditText et;

    public static Order or;
    JSONArray singleOrder;
    JSONObject jsonObj;
    Order order;

    String id, title, description, status, date, creatorId, executorId, customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_options);

        or = OrdersActivity.or;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.GetOrder(Integer.parseInt(or.getId()));
            }
        });

        try {
           // singleOrder = new JSONArray(RestClientService.resp);
            jsonObj = new JSONObject(RestClientService.resp);
            id = jsonObj.get("Id").toString();
            title = jsonObj.get("Title").toString();
            description = jsonObj.get("Description").toString();
            status = jsonObj.get("Status").toString();
            date = jsonObj.get("CreateDate").toString();
            creatorId = jsonObj.get("CreatorId").toString();
            executorId = jsonObj.get("ExecutorId").toString();
            customerId = jsonObj.get("CustomerId").toString();
            //
        }
        catch(JSONException e){}

         order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, true);




                et = (EditText) findViewById(R.id.ET_orders_settings);
        et.append(title.toString()+ "\n" + description.toString() + "\n" + date.toString());
    }

    public void editOrderOnClick(View v) {



        Intent myIntent = new Intent(v.getContext(), EditOrderActivity.class);
        OrdersActivitySettings.this.startActivity(myIntent);
        finish();



    }

    public void pinOrderOnClick(View v) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.EditOrder(Integer.parseInt(id), order);


                TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });

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
