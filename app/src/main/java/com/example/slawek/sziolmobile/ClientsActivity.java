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

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-04-12.
 */
public class ClientsActivity extends Activity{

    ///////////////

    // noiwa aktualizacja
    JSONArray clients;
     ///////////////////

    String firstName, lastName, address,clientId,teamId, team, city, street, homeNumber, flatNumber;
    String gpslat;  ///????
    String gpsLon;  ///????

    public static Client cl;

    List<String> listAdapter;

    List<Client> clientsList;
    ListView lv;
    Client client;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        lv = (ListView)findViewById(R.id.LV_clients);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.GetAllCustomers();
                try {
                    clients = new JSONArray(RestClientService.resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        AddClientsToListView();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        super.onResume();
        AddClientsToListView();
    }

    private void AddClientsToListView()
    {
        listAdapter = new ArrayList<String>();
        clientsList =  new ArrayList<Client>();

        for(int i=0; i<clients.length(); i++)
        {
            try {
                JSONObject jsonObj = clients.getJSONObject(i);
                firstName = jsonObj.get("FirstName").toString();
                lastName = jsonObj.get("LastName").toString();
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
                listAdapter.add(firstName+" "+lastName+"\n"+address);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            ArrayAdapter<Client> adap = new ArrayAdapter<Client>(this, android.R.layout.simple_list_item_1, clientsList);
            lv.setAdapter(adap);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    cl =   (Client)(lv.getItemAtPosition(position));

                    Intent myIntent = new Intent(view.getContext(), ClientsActivitySettings.class);
                    ClientsActivity.this.startActivityForResult(myIntent, 0);
                }
            });
        }
    }

    public void senderAddClientButtonOnClick(View v)
    {
        Intent intent = new Intent(ClientsActivity.this, NewClient.class);
        startActivity(intent);
    }
}
