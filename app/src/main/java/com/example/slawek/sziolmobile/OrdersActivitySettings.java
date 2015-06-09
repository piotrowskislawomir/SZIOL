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
import Models.NotificationModel;
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
    static Order order;

    String id, title, description, status, date, creatorId, executorId, customerId, teamId;
    String statusAktualny = "";

    public static Order getSingleOrderFromSettings()
    {
       return order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_options);

        try {


            NotificationModel notificationModel = null;

            if (getIntent() != null)
                if (getIntent().getExtras() != null)
                    if (getIntent().getExtras().getSerializable("notification") != null) {
                        notificationModel = (NotificationModel) getIntent().getExtras().getSerializable("notification");

                    }

            if (notificationModel != null) {
                or = new Order(notificationModel.getTicketId(), null);
            } else {
                or = Fragment_tickets.or;
            }

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            runOnUiThread(new Runnable() {
                public void run() {
                    RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                    RestService restService = new RestService(restClientService);
                    try
                    {
                        restService.GetOrder(Integer.parseInt(or.getId()));
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                        finish();
                    }
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


            } catch (JSONException e) {
            }

            // order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, true);

            order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, teamId, date, creatorId);


        et = (EditText) findViewById(R.id.ET_orders_settings);


        String address, city="", street = "", homeNumber="", flatNumber="", firstName="", lastName="";

        RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        RestService restService = new RestService(restClientService);
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

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                finish();
            }

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

        et.append("Klient: " + firstName + " " + lastName + "\n" + "Tytuł: " + title.toString()+ "\n" + "Opis: " + description.toString() + "\n" + "Status: " + aktualnyStatus +
                "\n" + "Ulica: " + street+ " " + homeNumber + flatNumber
                + "\n" + "Miejscowość: " + city);


/*
        if(status.toString().equalsIgnoreCase("CR"))
        {
            statusAktualny = "Gotowy do realizacji";
        }

        et.append("Tytuł: " + title.toString()+ "\n" + "Opis: " + description.toString() + "\n" + "Status: " + statusAktualny); //+ "\n" + date.toString());
 */


  //          et = (EditText) findViewById(R.id.ET_orders_settings);
 //           et.append(title.toString() + "\n" + description.toString() + "\n" + status.toString() + "\n" + date.toString());

        }
        catch (Exception ex)
        {
            finish();
        }

    }

    public void editSingleOrderOnClick(View v) {



        Intent myInt = new Intent(v.getContext(), EditOrderActivity.class);
       // OrdersActivitySettings.this.startActivity(myInt);
        startActivity(myInt);
        this.finish();




    }

    public void pinOrderOnClick(View v) {
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
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                    return;
                }
              //  TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });

        Intent myIntent = new Intent(v.getContext(), NavigationActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();


    }

    public void deleteOrderOnClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                try
                {
               restService.DeleteTicket(Integer.parseInt(order.getId()));

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                }

                //    TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });


        ////////////
        ////////////
        //////////////// TU ZMIENI I ODSWIEZAC WIDOK
        Intent myIntent = new Intent(v.getContext(), NavigationActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        OrdersActivitySettings.this.startActivity(myIntent);

        finish();


    }

}
