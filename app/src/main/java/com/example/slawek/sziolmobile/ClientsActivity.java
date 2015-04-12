package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    String firstName;
    String lastName;
    String address;
    String clientId;
    String teamId;
    String team;
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

               clientsList.add(new Client(clientId, firstName, lastName, address));
                listAdapter.add(firstName+" "+lastName+"\n"+address);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAdapter);
            lv.setAdapter(adapter);




            // clientsList.add(clients)
        }


    }
    public void senderAddClientButtonOnClick(View v)
    {

        Intent intent = new Intent(ClientsActivity.this, NewClient.class);
        //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
