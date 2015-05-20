package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
 * Created by Michał on 2015-05-10.
 */
public class CardItems extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_items);
        lv = (ListView)findViewById(R.id.LV_card_items);
        AddItemsFromCardToListView();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        super.onResume();
        AddItemsFromCardToListView();
    }

    private void AddItemsFromCardToListView()
    {
        cardList =  new ArrayList<Order>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.GetMyCard();
                try {
                    cardsItems = new JSONArray(RestClientService.resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        for(int i=0; i<cardsItems.length(); i++)
        {
            try {
                JSONObject jsonObj = cardsItems.getJSONObject(i);
                cardId = jsonObj.get("Id").toString();
                title = jsonObj.get("Title").toString();
                cardList.add(new Order(cardId.toString(), title.toString()));
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            ArrayAdapter<Order> adapt = new ArrayAdapter<Order>(this, android.R.layout.simple_list_item_1, cardList);
            lv.setAdapter(adapt);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,long id)
                {
                     card =   (Order)(lv.getItemAtPosition(position));
                     Intent myIntent = new Intent(view.getContext(), CardItemsDetails.class);
                     startActivity(myIntent);
                }
            });
        }
    }
}
