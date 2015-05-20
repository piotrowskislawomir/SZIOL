package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */

// assign to ticket false
    //executor id null
public class CardItemsDetails extends Activity {
    TextView et;

    public static Order or;
    JSONArray singleOrder;
    JSONObject jsonObj;
    static Order order;


    String id, title, description, status, date, creatorId, executorId, customerId, teamId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        or = CardItems.getCard();

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
            teamId = jsonObj.get("TeamId").toString();


        }
        catch(JSONException e){}

        // order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, true);

        order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, teamId, date, creatorId );



        et = (TextView) findViewById(R.id.TV_card_order_unpin);
        et.setText(title.toString()+ "\n" + description.toString() + "\n" + status.toString()+ "\n" + date.toString());
    }



    public void unPinOrderOnClick(View v) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                int status =  restService.unPinOrder(Integer.parseInt(id), order);

                if(status == 200)
                {
                    Toast.makeText(getApplicationContext(), "odpięto", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "NIE odpięto", Toast.LENGTH_LONG).show();
                }

                //  TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });


    //    Intent myIntent = new Intent(v.getContext(), EditOrderActivity.class);
    //    OrdersActivitySettings.this.startActivity(myIntent);
     //   finish();
    }

}


