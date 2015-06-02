
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
public class ClientsLivePlace extends Activity {

    ///////////////

    // noiwa aktualizacja
    JSONArray places;
    ///////////////////

    String firstName, lastName, address,clientId,teamId, team, city, street, homeNumber, flatNumber;
    String gpslat;  ///????
    String gpsLon;  ///????

    public static Coordinate cor;

    List<String> listAdapter;

    List<Coordinate> coordinatesList;
    ListView lv;
    Client client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_place);
        lv = (ListView)findViewById(R.id.LV_client_places);

                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
               // restService.GetAllCustomers();
                restService.GetClientPlaces(Fragment_new_client.getClient());
                try {
                    places = new JSONArray(RestClientService.resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        AddClientsToListView();
    }


    private void AddClientsToListView()
    {
        coordinatesList =  new ArrayList<Coordinate>();

        for(int i=0; i<places.length(); i++)
        {
            try {
                JSONObject jsonObj = places.getJSONObject(i);
                city = jsonObj.get("Name").toString();
                gpslat = jsonObj.get("Latitude").toString();
                gpsLon = jsonObj.get("Longitude").toString();

                coordinatesList.add(new Coordinate(Double.parseDouble(gpslat), Double.parseDouble(gpsLon), city));
             //   listAdapter.add(city);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            ArrayAdapter<Coordinate> adap = new ArrayAdapter<Coordinate>(this, android.R.layout.simple_list_item_1, coordinatesList);
            lv.setAdapter(adap);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    cor =   (Coordinate)(lv.getItemAtPosition(position));

                    RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                    RestService restService = new RestService(restClientService);
                   int restStatus = restService.AddNewCustomer(Fragment_new_client.getClient(), cor);

                    if(restStatus == 201)
                    {
                        Toast.makeText(getApplicationContext(), "Dodano klienta pomyślnie", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(view.getContext(), NavigationActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Nie dodano", Toast.LENGTH_LONG).show();
                    }

                    //      Intent myIntent = new Intent(view.getContext(), ClientsActivitySettings.class);
              //      ClientsActivity.this.startActivityForResult(myIntent, 0);
                }
            });
        }
    }

    public void senderAddClientButtonOnClick(View v)
    {
    //    Intent intent = new Intent(ClientsActivity.this, NewClient.class);
      //  startActivity(intent);
    }
}
