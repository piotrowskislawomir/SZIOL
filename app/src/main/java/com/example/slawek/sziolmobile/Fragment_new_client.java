package com.example.slawek.sziolmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-06-01.
 */
public class Fragment_new_client extends Fragment {

    View rootView;
    private EditText firstNameClient;
    private EditText lastNameClient;
    // private EditText addressClient;
    private EditText streetClient;
    private EditText homeNumberClient;
    private EditText flatNumberClient;
    private EditText cityClient;
    int restStatus;
    public static Client client;


    public static Client getClient()
    {
        return client;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.activity_add_new_client, container, false);

        firstNameClient = (EditText)rootView.findViewById(R.id.ET_new_client_name);
        lastNameClient = (EditText)rootView.findViewById(R.id.ET_new_client_last_name);
        cityClient = (EditText)rootView.findViewById(R.id.ET_new_client_city);
        streetClient = (EditText)rootView.findViewById(R.id.ET_new_client_street);
        homeNumberClient = (EditText)rootView.findViewById(R.id.ET_client_home_number);
        flatNumberClient = (EditText)rootView.findViewById(R.id.ET_new_client_flat_number);

        Button button = (Button) rootView.findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstNameClient.getText().toString().isEmpty() && !lastNameClient.getText().toString().isEmpty() &&
                        !cityClient.getText().toString().isEmpty() && !streetClient.getText().toString().isEmpty() &&
                        !homeNumberClient.getText().toString().isEmpty() /*&& !flatNumberClient.getText().toString().isEmpty()*/) {

                  client = new Client(firstNameClient.getText().toString(), lastNameClient.getText().toString(), cityClient.getText().toString(), streetClient.getText().toString(), homeNumberClient.getText().toString(), flatNumberClient.getText().toString());

                    Intent intent = new Intent(getActivity(), ClientsLivePlace.class);
                  //  Toast.makeText(getActivity().getApplicationContext(), "DOBRZE", Toast.LENGTH_LONG).show();

                    //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
            }
            else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "DUPA", Toast.LENGTH_LONG).show();

                }
            }
        });

        return rootView;
    }


    public void addNewClientButtonOnClick(View v)
    {

        if (!firstNameClient.getText().toString().isEmpty() && lastNameClient.getText().toString().isEmpty() &&
                cityClient.getText().toString().isEmpty() && streetClient.getText().toString().isEmpty() &&
                homeNumberClient.getText().toString().isEmpty() && flatNumberClient.getText().toString().isEmpty())
        {

            final Client client = new Client(firstNameClient.getText().toString(), lastNameClient.getText().toString(), cityClient.getText().toString(), streetClient.getText().toString(), homeNumberClient.getText().toString(), flatNumberClient.getText().toString());

                 RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                    RestService restService = new RestService(restClientService);
              //      restStatus = restService.AddNewCustomer(client);
                    // restService.DeleteCustomer(8);

       //             TextView tv = (TextView) findViewById(R.id.textView3);
          //   }
            if(restStatus == 200)
            {
                Intent intent = new Intent(getActivity(), Fragment_clients.class);
           //     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
            else
            {
                Toast.makeText(getActivity(), "Zła odpowiedź serwera", Toast.LENGTH_SHORT).show();

            }
        }

        else
        {
            Toast.makeText(getActivity(), "Proszę uzupełnić wszystkie informacje o kliencie", Toast.LENGTH_SHORT).show();

        }
    }}


