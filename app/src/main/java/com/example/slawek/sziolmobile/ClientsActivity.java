package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Client;
import sziolmobile.RestClientService;

/**
 * Created by Michał on 2015-04-12.
 */
public class ClientsActivity extends Activity{

    /////////////////

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
        clientsList =  new ArrayList<Client>();

        for(int i=0; i<MainMenu.clients.length(); i++)
        {
            try {
                JSONObject jsonObj = MainMenu.clients.getJSONObject(i);
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
             //  clientsList.add(new Client(clientId, firstName, lastName, address));
                listAdapter.add(firstName+" "+lastName+"\n"+address);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAdapter);
            lv.setAdapter(adapter);

            ArrayAdapter<Client> adap = new ArrayAdapter<Client>(this,
                    android.R.layout.simple_list_item_1, clientsList);

            lv.setAdapter(adap);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

//
                 //   String cl =  adapter.getItem(position);

                    cl =   (Client)(lv.getItemAtPosition(position));


                    Intent myIntent = new Intent(view.getContext(), ClientsActivitySettings.class);
                    ClientsActivity.this.startActivityForResult(myIntent, 0);

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
        Intent intent = new Intent(ClientsActivity.this, NewClient.class);
        //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
       // finish();

    }
}
