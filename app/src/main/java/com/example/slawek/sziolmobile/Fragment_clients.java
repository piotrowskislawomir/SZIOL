package com.example.slawek.sziolmobile;


//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
 * Created by Michał on 2015-05-31.
 */
public class Fragment_clients extends Fragment {

    View rootView;
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

    public static Client getClient()
    {
        return cl;
    }

    @Nullable
    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.activity_client_menu, container, false);
        lv = (ListView)rootView.findViewById(R.id.LV_clients);

           Button button = (Button) rootView.findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), NewClient.class);
                startActivity(intent);
            }
        });

        RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        RestService restService = new RestService(restClientService);
        try
        {
        restService.GetAllCustomers();

        try {
            clients = new JSONArray(RestClientService.resp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AddClientsToListView();

        } catch (Exception ex) {
            Toast.makeText(rootView.getContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
        }
        return rootView;
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

            ArrayAdapter<Client> adap = new ArrayAdapter<Client>(getActivity(), android.R.layout.simple_list_item_1, clientsList);
            lv.setAdapter(adap);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cl = (Client) (lv.getItemAtPosition(position));
                    Intent myIntent = new Intent(view.getContext(), ClientsActivitySettings.class);
                    startActivity(myIntent);
                }
            });
        }
    }
}