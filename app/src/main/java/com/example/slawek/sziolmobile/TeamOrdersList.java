
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
public class TeamOrdersList extends Activity {

    ///////////////

    // noiwa aktualizacja
    JSONArray teamOrders;
    ///////////////////

    String firstName;
    String lastName;
    String address; // ??????
    String clientId;
    String teamId;
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
    static Order ord;


    public static Order getCustomerOrder()
    {
        return ord;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_team_orders_card);
        lv = (ListView)findViewById(R.id.LV_customer_orders);
        ////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                // restService.GetAllCustomers();
               // cr = TeamOrders.getCard();
               cr = Fragment_my_team.getCard();
                try
                {
                    teamOrders = new JSONArray();
                    restService.GetCustomerCard(cr.getCardId());
                    try {
                        teamOrders = new JSONArray(RestClientService.resp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        ////

        AddClientsToListView();
    }



    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        super.onResume();
        AddClientsToListView();
    }

    private void AddClientsToListView()
    {
        listAdapter = new ArrayList<String>();
        orderList =  new ArrayList<Order>();

        for(int i=0; i<teamOrders.length(); i++)
        {
            try {
                JSONObject jsonObj = teamOrders.getJSONObject(i);
     //           firstName = jsonObj.getJSONObject("Worker").getString("FirstName").toString();
      //          lastName = jsonObj.getJSONObject("Worker").getString("LastName").toString();
                orderId = jsonObj.get("Id").toString();
                titleOrder = jsonObj.get("Title").toString();
                /*
                address = jsonObj.get("Address").toString();
                teamId = jsonObj.get("TeamId").toString();
                clientId = jsonObj.get("Id").toString();
                team = jsonObj.get("Team").toString();
                city = jsonObj.get("City").toString();
                street = jsonObj.get("Street").toString();
                homeNumber = jsonObj.get("HomeNo").toString();
                flatNumber = jsonObj.get("FlatNo").toString();
                gpslat = jsonObj.get("GpsLatitude").toString();
                gpsLon = jsonObj.get("GpsLongitude").toString();

                clientsList.add(new Client(clientId, firstName, lastName, city, street, homeNumber, flatNumber));
                //  clientsList.add(new Client(clientId, firstName, lastName, address));
                listAdapter.add(firstName+" "+lastName+"\n"+address);
            */
           //     card = new Card(Integer.parseInt(orderId), firstName, lastName);
ord = new Order(orderId, titleOrder);
                orderList.add(ord);
                //     listAdapter.add(firstName+" "+lastName);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAdapter);
            lv.setAdapter(adapter);

            ArrayAdapter<Order> adap = new ArrayAdapter<Order>(this,
                    android.R.layout.simple_list_item_1, orderList);

            lv.setAdapter(adap);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    ord =   (Order)(lv.getItemAtPosition(position));


                    Intent myIntent = new Intent(view.getContext(), TeamOrdersListDetails.class);
                    startActivity(myIntent);
//                    ClientsActivity.this.startActivityForResult(myIntent, 0);



                }
            });


            // clientsList.add(clients)
        }


    }
    public void senderAddClientButtonOnClick(View v)
    {
        //   Intent intent = new Intent(ClientsActivity.this, NewClient.class);
        //    startActivity(intent);

    }
}
