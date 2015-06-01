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
 * Created by Michał on 2015-05-31.
 */
public class Fragment_tickets extends Fragment {

    View rootView;
    String title;
    String description;
    String status;
    String id;
    JSONArray orders;
    public static Order or;
    List<Order> ordersList;
    ListView lv;
    Client client;


    public static Order getOrder()
    {
        return or;
    }

  @Nullable
  @Override
  public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
      rootView = inflater.inflate(R.layout.activity_order_menu, container, false);

      lv = (ListView)rootView.findViewById(R.id.LV_orders);

              RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
              RestService restService = new RestService(restClientService);
              restService.GetAllOrders();
              try {
                  orders = new JSONArray(RestClientService.resp);
              } catch (JSONException e) {
                  e.printStackTrace();
              }

      AddOrdersToListView();

      return rootView;
  }

    private void AddOrdersToListView()
    {
        ordersList =  new ArrayList<Order>();

        for(int i=0; i<orders.length(); i++) {
            try {
                JSONObject jsonObj = orders.getJSONObject(i);
                id = jsonObj.get("Id").toString();
                title = jsonObj.get("Title").toString();
                description = jsonObj.get("Description").toString();
                //            status = jsonObj.get("Status").toString();
                //           customerId = jsonObj.get("CustomerId").toString();


                ordersList.add(new Order(id.toString(), title.toString()));
                //  clientsList.add(new Client(clientId, firstName, lastName, address));
                //  listAdapter.add(firstName+" "+lastName+"\n"+address);
                //   Toast.makeText(getApplicationContext(), ordersList.get(1),Toast.LENGTH_LONG, );

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listAdapter);
            //       lv.setAdapter(adapter);

            ArrayAdapter<Order> adapt = new ArrayAdapter<Order>(getActivity(),
                    android.R.layout.simple_list_item_1, ordersList);

            lv.setAdapter(adapt);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

//
                    //   String cl =  adapter.getItem(position);

                    or = (Order) (lv.getItemAtPosition(position));


                    Intent myIntent = new Intent(view.getContext(), OrdersActivitySettings.class);
                // -------------    OrdersActivity.this.startActivityForResult(myIntent, 0);

                    //   int color = parent.getAdapter().getItem(position);

                    //  Toast.makeText(getBaseContext(), cl.getCity(), Toast.LENGTH_LONG).show();
                     startActivity(myIntent);

                }
            });


            // clientsList.add(clients)
        }


    }
}
