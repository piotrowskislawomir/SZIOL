package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Card;
import Models.Client;

/**
 * Created by Michał on 2015-05-10.
 */
public class CardItems extends Activity {

    String title;
    String cardId;

    //String firstName;
    //String lastName;

    public static Order card;


    List<Order> cardList;
    ListView lv;
    Client client;
    ArrayAdapter<String> adapter;


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
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        super.onResume();
        AddItemsFromCardToListView();
    }

    private void AddItemsFromCardToListView()
    {
        cardList =  new ArrayList<Order>();

        for(int i=0; i<MainMenu.cardsItems.length(); i++)
        {
            try {
                JSONObject jsonObj = MainMenu.cardsItems.getJSONObject(i);
                cardId = jsonObj.get("Id").toString();
                title = jsonObj.get("Title").toString();
                //            status = jsonObj.get("Status").toString();
                //           customerId = jsonObj.get("CustomerId").toString();


                cardList.add(new Order(cardId.toString(), title.toString()));

            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            //       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAdapter);
            //       lv.setAdapter(adapter);

            ArrayAdapter<Order> adapt = new ArrayAdapter<Order>(this,
                    android.R.layout.simple_list_item_1, cardList);

            lv.setAdapter(adapt);

            /*
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

//
                    //   String cl =  adapter.getItem(position);

                    card =   (Card)(lv.getItemAtPosition(position));


                    Intent myIntent = new Intent(view.getContext(), OrdersActivitySettings.class);
                    OrdersActivity.this.startActivityForResult(myIntent, 0);

                    //   int color = parent.getAdapter().getItem(position);

                    //  Toast.makeText(getBaseContext(), cl.getCity(), Toast.LENGTH_LONG).show();



                }
            });


            // clientsList.add(clients)
*/
        }


    }
}
