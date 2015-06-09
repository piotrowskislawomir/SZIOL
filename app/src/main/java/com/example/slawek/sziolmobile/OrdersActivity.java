package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class OrdersActivity extends Activity {
    /////////////////

    ///////////////////

   String title;
    String description;
    String status;
    String customerId;
    String id;

    String gpslat;  ///????
    String gpsLon;  ///????

    JSONArray orders;

    public static Order or;

    List<String> listAdapter;

    List<Order> ordersList;
    ListView lv;
    Client client;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);
        lv = (ListView)findViewById(R.id.LV_orders);

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

        AddOrdersToListView();
    }



    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        super.onResume();
        AddOrdersToListView();
    }


    private void AddOrdersToListView()
    {
        ordersList =  new ArrayList<Order>();

        for(int i=0; i<orders.length(); i++)
        {
            try {
                JSONObject jsonObj = orders.getJSONObject(i);
                id = jsonObj.get("Id").toString();
                title = jsonObj.get("Title").toString();
                description = jsonObj.get("Description").toString();
    //            status = jsonObj.get("Status").toString();
    //           customerId = jsonObj.get("CustomerId").toString();


                ordersList.add(new Order(id.toString(), title.toString()));
                //  clientsList.add(new Client(clientId, firstName, lastName, address));
              //  listAdapter.add(firstName+" "+lastName+"\n"+address);
             //   Toast.makeText(getApplicationContext(), ordersList.get(1),Toast.LENGTH_LONG, );

            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

     //       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAdapter);
     //       lv.setAdapter(adapter);

            ArrayAdapter<Order> adapt = new ArrayAdapter<Order>(this,
                    android.R.layout.simple_list_item_1, ordersList);

            lv.setAdapter(adapt);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

//
                    //   String cl =  adapter.getItem(position);

                    or =   (Order)(lv.getItemAtPosition(position));


    Intent myIntent = new Intent(view.getContext(), OrdersActivitySettings.class);
    OrdersActivity.this.startActivityForResult(myIntent, 0);

                    //   int color = parent.getAdapter().getItem(position);

                    //  Toast.makeText(getBaseContext(), cl.getCity(), Toast.LENGTH_LONG).show();



                }
            });


            // clientsList.add(clients)
        }


    }

    public void senderAddClientButtonOnClick(View v)
    {
        // Intent i = new Intent(this, SecondActivity.class);
        // startActivityForResult(i, 1);
        Intent intent = new Intent(OrdersActivity.this, NewOrderActivity.class);
        //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();

    }

}
