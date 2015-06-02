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
import android.widget.Switch;

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
public class Fragment_settings extends Fragment {


    View rootView;
    Switch swtch;
   @Nullable
    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_switch, container, false);
        swtch = (Switch)rootView.findViewById(R.id.switch1);
        if(MainActivity.NOTIFICATION_SERVICE_FLAG && MainActivity.GPS_SERVICE_FLAG)
        {
            swtch.setChecked(true);
        }
        if(!MainActivity.NOTIFICATION_SERVICE_FLAG && !MainActivity.GPS_SERVICE_FLAG)
        {
            swtch.setChecked(false);
        }
        swtch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent serviceIntent = new Intent(getActivity(), GpsService.class);
                Intent serviceIntent2 = new Intent(getActivity(), NotificationService.class);
                if(swtch.isChecked())
                {
                    getActivity().startService(serviceIntent2);
                    getActivity().startService(serviceIntent);
                    MainActivity.NOTIFICATION_SERVICE_FLAG = true;
                    MainActivity.GPS_SERVICE_FLAG = true;

                }
                if(!swtch.isChecked())
                {
                    getActivity().stopService(serviceIntent2);
                    getActivity().stopService(serviceIntent);
                    MainActivity.NOTIFICATION_SERVICE_FLAG = false;
                    MainActivity.GPS_SERVICE_FLAG = false;
                }
            }
        });


        return rootView;
    }
}
