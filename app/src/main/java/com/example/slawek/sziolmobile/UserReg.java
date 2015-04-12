package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import Models.User;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-04-11.
 */
public class UserReg extends Activity {

    private TextView login;
    private TextView pass;
    private  TextView pass2;
    private TextView firstName;
    private TextView lastName;
    private TextView teamKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        login = (TextView)findViewById(R.id.ET_log_reg);
        pass = (TextView)findViewById(R.id.ET_pass_reg);
        pass2 = (TextView)findViewById(R.id.ET_pass_reg_2);
        firstName = (TextView)findViewById(R.id.ET_name_reg);
        lastName = (TextView)findViewById(R.id.ET_lastName_reg);
        teamKey = (TextView)findViewById(R.id.ET_reg_key);
    }


    public void registerButtonOnClick(View v) {
        final User newUser = new User(login.getText().toString(), pass.getText().toString(), firstName.getText().toString(), lastName.getText().toString());
        final String teamKeyActiv = teamKey.getText().toString();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.SendClientRegistry(newUser, teamKeyActiv);
            }
        });
    }
}
