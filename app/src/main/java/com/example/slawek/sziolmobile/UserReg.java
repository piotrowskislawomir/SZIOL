package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Telephony;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Models.User;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-04-11.
 */
public class UserReg extends Activity {

    private EditText login;
    private EditText pass;
    private EditText pass2;
    private EditText firstName;
    private EditText lastName;
    private EditText teamKey;
    public static TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        tv = (TextView) findViewById(R.id.textView7);
        login = (EditText) findViewById(R.id.ET_log_reg);
        pass = (EditText) findViewById(R.id.ET_pass_reg);
        pass2 = (EditText) findViewById(R.id.ET_pass_reg_2);
        firstName = (EditText) findViewById(R.id.ET_name_reg);
        lastName = (EditText) findViewById(R.id.ET_lastName_reg);
        teamKey = (EditText) findViewById(R.id.ET_reg_key);
    }


    public void registerButtonOnClick(View v) {

        if(!tv.getText().toString().isEmpty() && !login.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !pass2.getText().toString().isEmpty()
                && !firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty()&& !teamKey.getText().toString().isEmpty()) {
            // equals ignore ?>>>
            if (pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())) {
                final User newUser = new User(login.getText().toString(), pass.getText().toString(), firstName.getText().toString(), lastName.getText().toString());
                final String teamKeyActivity = teamKey.getText().toString();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                runOnUiThread(new Runnable() {
                    public void run() {
                        RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                        RestService restService = new RestService(restClientService);

                        try
                        {
                        int restStatus = restService.SendClientRegistry(newUser, Integer.parseInt(teamKeyActivity));
                        if(restStatus==200)
                        {
                            Toast.makeText(getApplicationContext(), "Rejestracja powiodła się pomyślnie", Toast.LENGTH_SHORT).show();
                            try {
                                Thread.sleep(8000);
                                Intent myIntent = new Intent(UserReg.this, UserLog.class);
                                UserReg.this.startActivity(myIntent);
                                finish();
                            }
                            catch(InterruptedException ex)
                            {

                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Rejestracja nie powiodła się", Toast.LENGTH_SHORT).show();
                        }

                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                });
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Hasła są różne!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą zostać uzupełnione", Toast.LENGTH_LONG).show();
        }
    }
}
