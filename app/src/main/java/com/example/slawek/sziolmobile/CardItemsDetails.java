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

public class CardItemsDetails extends Activity {

    TextView et;
    public static Order or;
    JSONArray singleOrder;
    JSONObject jsonObj;
    static Order order;
    String id, title, description, status, date, creatorId, executorId, customerId, teamId;
    Client cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

      //  or = CardItems.getCard();
or = Fragment_my_card.getCard();
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



        order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, teamId, date, creatorId );

        ///////////

        String address, city="", street = "", homeNumber="", flatNumber="", firstName="", lastName="";

        RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        RestService restService = new RestService(restClientService);
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

        et = (TextView) findViewById(R.id.TV_card_order_unpin);

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

        et.setText("Klient: " + firstName + " " + lastName + "\n" + "Tytuł: " + title.toString()+ "\n" + "Opis: " + description.toString() + "\n" + "Status: " + aktualnyStatus +
                 "\n" + "Ulica: " + street+ " " + homeNumber + flatNumber
                + "\n" + "Miejscowość: " + city);

    }

    public void unpinOrderOnClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                int status =  restService.unPinOrder(Integer.parseInt(id), order);

                if(status == 200)
                {
                    Toast.makeText(getApplicationContext(), "Opięto zlecenie", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie można odpiąć", Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent myIntent = new Intent(v.getContext(), NavigationActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();
    }

        public void executeOrderOnClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                int status =  restService.executeOrder(Integer.parseInt(id), order);

                if(status == 200)
                {
                    Toast.makeText(getApplicationContext(), "W trakcie realizacji", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie można realizować", Toast.LENGTH_LONG).show();
                }
        }
        });

        Intent myIntent = new Intent(v.getContext(), NavigationActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();
    }

    public void closeOrderOnClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                int status =  restService.closeOrder(Integer.parseInt(id), order);

                if(status == 200)
                {
                    Toast.makeText(getApplicationContext(), "Zamknięto zlecenie", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie możńa zamknąć zlecenia", Toast.LENGTH_LONG).show();
                }
            }
        });

        Intent myIntent = new Intent(v.getContext(), NavigationActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();
    }


}


