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
public class ClientsActivitySettings extends Activity {

    EditText et;
    JSONObject jsonObj;

    private static Client singleClient;
    String id;
    String firstName;
    String lastName;
    String address;
    String teamId;
    String team;
    String city;
    String street;
    String homeNumber;
    String flatNumber;
    String gpsLatitude;
    String gpsLongitude;


    public static Client  cl;

    public static Client getSingleClient()
    {
        return singleClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_options);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);

                try
                {
                    restService.GetClientById(Integer.parseInt(Fragment_clients.getClient().getId()));

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        try {
            try {
                jsonObj = new JSONObject(RestClientService.resp);
                id = jsonObj.get("Id").toString();
                firstName = jsonObj.get("FirstName").toString();
                lastName = jsonObj.get("LastName").toString();
                address = jsonObj.get("Address").toString();
                teamId = jsonObj.get("TeamId").toString();
                team = jsonObj.get("Team").toString();
                city = jsonObj.get("City").toString();
                street = jsonObj.get("Street").toString();
                homeNumber = jsonObj.get("HomeNo").toString();
                street = jsonObj.get("Street").toString();
                flatNumber = jsonObj.get("FlatNo").toString();
                gpsLatitude = jsonObj.get("GpsLatitude").toString();
                gpsLongitude = jsonObj.get("GpsLongitude").toString();
            } catch (JSONException e) {
            }

            singleClient = new Client(id, firstName, lastName, city, street, homeNumber, flatNumber, gpsLatitude, gpsLongitude, teamId, team);

            et = (EditText) findViewById(R.id.ET_clients_settings);
            et.append("Imię: " + firstName + "\n" +
                    "Nazwisko: " + lastName + "\n" +
                    "Ulica " + street + "\n" +
                    "Numer domu: " + homeNumber + "\n" +
                    "Numer mieszkania: " + flatNumber + "\n" +
                    "Miejscowość: " + city);

        }
        catch(Exception ex)
        {}


    }

    public void editClientOnClick(View v) {
        Intent myIntent = new Intent(v.getContext(), EditClientActivity.class);
        ClientsActivitySettings.this.startActivity(myIntent);
       this.finish();
    }

    public void deleteClientOnClick(View v) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.DeleteCustomer(ClientsActivity.cl.getId());
            }
        });


        Intent myIntent = new Intent(v.getContext(), MainMenu.class);
        ClientsActivitySettings.this.startActivity(myIntent);
        finish();


    }

    public void addNewOrderOnClick(View v)
    {
        Intent myIntent = new Intent(v.getContext(), NewOrderActivity.class);
        ClientsActivitySettings.this.startActivity(myIntent);
       // finish();
    }
}
