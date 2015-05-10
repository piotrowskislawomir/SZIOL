package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Client;

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

        for(int i=0; i<MainMenu.orders.length(); i++)
        {
            try {
                JSONObject jsonObj = MainMenu.orders.getJSONObject(i);
                title = jsonObj.get("Title").toString();
                description = jsonObj.get("Title").toString();
                status = jsonObj.get("Status").toString();
               customerId = jsonObj.get("CustomerId").toString();


                ordersList.add(new Order(title.toString(), description.toString(), status.toString(), Integer.parseInt(customerId.toString())));
                //  clientsList.add(new Client(clientId, firstName, lastName, address));
              //  listAdapter.add(firstName+" "+lastName+"\n"+address);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAdapter);
            lv.setAdapter(adapter);

            ArrayAdapter<Order> adap = new ArrayAdapter<Order>(this,
                    android.R.layout.simple_list_item_1, ordersList);

            lv.setAdapter(adap);

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

}
