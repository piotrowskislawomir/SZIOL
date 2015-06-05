package com.example.slawek.sziolmobile;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

/**
 * Created by Michał on 2015-05-31.
 */
public class Fragment_settings extends Fragment {

    private SharedPropertiesManager sharedPropertiesManager;
    private Resources resources;

    View rootView;
    Switch switchLocalization;
    Switch switchNotification;

    public Fragment_settings()
    {}

    public Fragment_settings(Context context)
    {
            resources = context.getResources();
            sharedPropertiesManager = new SharedPropertiesManager(context);
    }

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_switch, container, false);
        switchLocalization = (Switch)rootView.findViewById(R.id.switchLocalization);
        switchNotification = (Switch)rootView.findViewById(R.id.switchNotifiaction);

        switchLocalization.setChecked(Boolean.parseBoolean(sharedPropertiesManager.GetValue(resources.getString(R.string.shared_localization_enable), "false")));
        switchNotification.setChecked(Boolean.parseBoolean(sharedPropertiesManager.GetValue(resources.getString(R.string.shared_notification_enable), "true")));

        switchLocalization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPropertiesManager.SetValue(resources.getString(R.string.shared_localization_enable), Boolean.toString(switchLocalization.isChecked()));
            }
        });

        switchNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPropertiesManager.SetValue(resources.getString(R.string.shared_notification_enable), Boolean.toString(switchNotification.isChecked()));
            }
        });

        return rootView;
    }
}
