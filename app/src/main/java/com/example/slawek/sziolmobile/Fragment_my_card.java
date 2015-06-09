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

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-31.
 */
public class Fragment_my_card extends Fragment {

    View rootView;
    String title;
    String cardId;
    JSONArray cardsItems;
    static Order card;
    List<Order> cardList;
    ListView lv;
    Client client;

    public static Order getCard()
    {
        return card;
    }

    @Nullable
    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.activity_card_items, container, false);
        lv = (ListView)rootView.findViewById(R.id.LV_card_items);
        AddItemsFromCardToListView();

        return rootView;
    }

    private void AddItemsFromCardToListView() {
        cardList = new ArrayList<Order>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                try
                {
                restService.GetMyCard();
                try {
                    cardsItems = new JSONArray(RestClientService.resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        for (int i = 0; i < cardsItems.length(); i++) {
            try {
                JSONObject jsonObj = cardsItems.getJSONObject(i);
                cardId = jsonObj.get("Id").toString();
                title = jsonObj.get("Title").toString();
                cardList.add(new Order(cardId.toString(), title.toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<Order> adapt = new ArrayAdapter<Order>(getActivity(), android.R.layout.simple_list_item_1, cardList);
            lv.setAdapter(adapt);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    card = (Order) (lv.getItemAtPosition(position));
                    Intent myIntent = new Intent(view.getContext(), CardItemsDetails.class);
                    startActivity(myIntent);
                }
            });
        }
                } catch (Exception ex) {
                    Toast.makeText(getActivity().getBaseContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                }
    }
}