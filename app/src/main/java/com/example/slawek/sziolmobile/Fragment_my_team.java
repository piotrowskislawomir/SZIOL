package com.example.slawek.sziolmobile;

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
import android.widget.ListView;
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
 * Created by Michał on 2015-06-01.
 */
public class Fragment_my_team extends Fragment{

    View rootView;
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

    String orderId;

    String gpslat;  ///????
    String gpsLon;  ///????

    public static Client cl;

    List<String> listAdapter;

    List<Card> cardList;
    ListView lv;
    private static Card card;
    ArrayAdapter<String> adapter;

    public static Card getCard()
    {
        return card;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.activity_team_orders, container, false);

        lv = (ListView)rootView.findViewById(R.id.LV_orders);
        ////

                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
        try
        {
                restService.GetAllTeam();
                try {
                    teamOrders = new JSONArray(RestClientService.resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


        AddClientsToListView();
        } catch (Exception ex) {
            Toast.makeText(getActivity().getBaseContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    private void AddClientsToListView() {
        listAdapter = new ArrayList<String>();
        cardList = new ArrayList<Card>();

        for (int i = 0; i < teamOrders.length(); i++) {
            try {
                JSONObject jsonObj = teamOrders.getJSONObject(i);
                firstName = jsonObj.getJSONObject("Worker").getString("FirstName").toString();
                lastName = jsonObj.getJSONObject("Worker").getString("LastName").toString();
                orderId = jsonObj.get("CardId").toString();
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
                card = new Card(Integer.parseInt(orderId), firstName, lastName);

                cardList.add(card);
                //     listAdapter.add(firstName+" "+lastName);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            ArrayAdapter<Card> adap = new ArrayAdapter<Card>(getActivity(),
                    android.R.layout.simple_list_item_1, cardList);

            lv.setAdapter(adap);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    card = (Card) (lv.getItemAtPosition(position));


                    Intent myIntent = new Intent(view.getContext(), TeamOrdersList.class);
                    startActivity(myIntent);
//                    ClientsActivity.this.startActivityForResult(myIntent, 0);


                }
            });


            // clientsList.add(clients)
        }
    }

}
