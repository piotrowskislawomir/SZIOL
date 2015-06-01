package com.example.slawek.sziolmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-06-01.
 */
public class Fragment_new_ticket extends Fragment {
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
        rootView = inflater.inflate(R.layout.fragment_client_menu, container, false);
        lv = (ListView)rootView.findViewById(R.id.LV_clients);


        RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        RestService restService = new RestService(restClientService);
        restService.GetAllCustomers();
        try {
            clients = new JSONArray(RestClientService.resp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AddClientsToListView();
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
                    Intent myIntent = new Intent(view.getContext(), Fragment_activity_client_settings.class);
                    startActivity(myIntent);
                }
            });
        }
    }
}
