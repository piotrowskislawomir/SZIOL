
/**
 * Created by Michał on 2015-05-19.
 */


package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Card;
import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-04-12.
 */
public class TeamOrdersListDetails extends Activity {

    ///////////////

    // noiwa aktualizacja
    JSONArray teamOrders;
    ///////////////////

    String id, title, description, status, date, creatorId, executorId, customerId, teamId;


    String firstName;
    String lastName;
    String address; // ??????
    String clientId;
     String team;
    String city;
    String street;
    String homeNumber;
    String flatNumber;
    String titleOrder;

    String orderId;

    String gpslat;  ///????
    String gpsLon;  ///????

    public static Client cl;

    List<String> listAdapter;

    List<Order> orderList;
    ListView lv;
    private static Card card;
    ArrayAdapter<String> adapter;
    Card cr;
    Order ord;
    JSONObject jsonObj;
    Order order;
    EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_orders_card_details);
        ////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                // restService.GetAllCustomers();

                ord = TeamOrdersList.getCustomerOrder();
                //cr = TeamOrders.getCard();

                restService.GetOrder(Integer.parseInt(ord.getId()));


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



                et = (EditText) findViewById(R.id.ET_team_card_order_details);
                et.append(title.toString()+ "\n" + description.toString() + "\n" + status.toString()+ "\n" + date.toString());

            }
        });
        ////

    }



    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        super.onResume();
    }

    public void senderAddClientButtonOnClick(View v)
    {
        //   Intent intent = new Intent(ClientsActivity.this, NewClient.class);
        //    startActivity(intent);

    }
}
