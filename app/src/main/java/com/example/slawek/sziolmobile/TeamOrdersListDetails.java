
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
  //  Order ord;
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


                //cr = TeamOrders.getCard();

                try
                {
                    order = TeamOrdersList.getCustomerOrder();
                    restService.GetOrder(Integer.parseInt(order.getId()));
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                    finish();
                }

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

                 order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, true);

             //   RestService restService = new RestService(restClientService);
                try
                {
                    restService.GetClientById(Integer.parseInt(customerId));
                    try {
                        jsonObj = new JSONObject(RestClientService.resp);
                        address = jsonObj.get("Address").toString();
                        firstName = jsonObj.get("FirstName").toString();
                        lastName = jsonObj.get("LastName").toString();

                        city = jsonObj.get("City").toString();
                        street = jsonObj.get("Street").toString();
                        homeNumber = jsonObj.get("HomeNo").toString();
                        flatNumber = jsonObj.get("FlatNo").toString();
                    }
                    catch(JSONException e){}

        /*
         this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
        this.homeNumber = homeNumber;
        this.flatNumber = flatNumber;
        */

                //////

                if(!flatNumber.isEmpty())
                {
                    flatNumber = "/"+flatNumber;
                }

                //   et = (TextView) findViewById(R.id.TV_card_order_unpin);

                String aktualnyStatus = "";

                if(status.toString().equalsIgnoreCase("AS"))
                {
                    aktualnyStatus = "Przypisane";
                }
                if(status.toString().equalsIgnoreCase("EX"))
                {
                    aktualnyStatus = "Realizowane";
                }
                if(status.toString().equalsIgnoreCase("CL"))
                {
                    aktualnyStatus = "Wykonano";
                }
                if(status.toString().equalsIgnoreCase("CR"))
                {
                    aktualnyStatus = "Gotowy do realizacji";
                }

                et = (EditText) findViewById(R.id.ET_team_card_order_details);


                et.append("Klient: " + firstName + " " + lastName + "\n" + "Tytuł: " + title.toString()+ "\n" + "Opis: " + description.toString() + "\n" + "Status: " + aktualnyStatus +
                        "\n" + "Ulica: " + street+ " " + homeNumber + flatNumber
                        + "\n" + "Miejscowość: " + city);


            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                finish();
            }
            }
        });
        ////

    }


    public void takeOrderOtherCustomer(View v)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);

                try
                {
                    int status =  restService.PinOrder(Integer.parseInt(id), order);

                    if(status == 200)
                    {
                        Toast.makeText(getApplicationContext(), "przypięcie ok", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "przypięcie NIE ok", Toast.LENGTH_LONG).show();
                    }

                //  TextView tv = (TextView) findViewById(R.id.textView3);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        Intent myIntent = new Intent(v.getContext(), NavigationActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();



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
