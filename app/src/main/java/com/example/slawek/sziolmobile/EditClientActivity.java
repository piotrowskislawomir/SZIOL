package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class EditClientActivity extends Activity{
    EditText fn, ln, flat, home, street, city;

    Button bt;
    Client singleClientEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        singleClientEdit = ClientsActivitySettings.getSingleClient();

        bt = (Button)findViewById(R.id.BT_edit_client_save);

        fn = (EditText) findViewById(R.id.ET_edit_firstName);
        ln = (EditText) findViewById(R.id.ET_edit_lastName);
        flat = (EditText) findViewById(R.id.ET_edit_flatNumber);
        street = (EditText) findViewById(R.id.ET_edit_street);
        city = (EditText) findViewById(R.id.ET_edit_city);
        home = (EditText) findViewById(R.id.ET_edit_homeNumber);

        fn.setText(singleClientEdit.getFirstName().toString());
        ln.setText(singleClientEdit.getLastName().toString());
        flat.setText(singleClientEdit.getFlatNumber().toString());
        street.setText(singleClientEdit.getStreet().toString());
        home.setText(singleClientEdit.getHomeNumber().toString());
        city.setText(singleClientEdit.getCity().toString());


       // Client client = new Client(ClientsActivitySettings.cl.getId());

    }

      public void saveEditClientOnClick(View v) {

          singleClientEdit.updateData(singleClientEdit.getId().toString(),
                                      fn.getText().toString(), ln.getText().toString(), city.getText().toString(), street.getText().toString(),
                  home.getText().toString(), flat.getText().toString(),
                                      singleClientEdit.getGpsLatitude().toString(), singleClientEdit.getGpsLongitude().toString(), singleClientEdit.getTeamId(), singleClientEdit.getTeam());

          StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
          StrictMode.setThreadPolicy(policy);

          runOnUiThread(new Runnable() {
              public void run() {
                  RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                  RestService restService = new RestService(restClientService);
                  restService.EditCustomer(Integer.parseInt(singleClientEdit.getId()), singleClientEdit);


                 // TextView tv = (TextView) findViewById(R.id.textView3);
                  Intent intent = new Intent(EditClientActivity.this, ClientsActivity.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(intent);
                //  finish();

              }
          });

          //Intent intent = new Intent(NewClient.this, MainMenu.class);
          //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          //startActivity(intent);
          //finish();



       // Intent myIntent = new Intent(v.getContext(), MainMenu.class);
      //  ClientsActivitySettings.this.startActivityForResult(myIntent, 0);




    }
}
