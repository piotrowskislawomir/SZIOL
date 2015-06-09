package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-05-10.
 */
public class EditOrderActivity extends Activity {
    EditText title, desc, status; /// i tutaj inne, home, street, city;

    Button bt;

    Order ord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        ord = OrdersActivitySettings.getSingleOrderFromSettings();

        bt = (Button)findViewById(R.id.BT_savae_change_order);

        title = (EditText)findViewById(R.id.ET_edit_order_title);
        desc = (EditText)findViewById(R.id.ET_edit_order_desc);
        status = (EditText)findViewById(R.id.ET_edit_order_status);



        title.setText(ord.getTitle().toString());
        desc.setText(ord.getDescription().toString());
        status.setText(ord.getStatus().toString());


        // Client client = new Client(ClientsActivitySettings.cl.getId());

    }

    public void saveEditOrderOnClick(View v) {

       ord.setDescription(desc.getText().toString());
        ord.setTitle(title.getText().toString());
       // ord.setStatus(status.getText().toString());
        ord.setStatus("CR");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);

                try
                {
                 restService.EditOrder(Integer.parseInt(ord.getId()), ord);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        Intent intent = new Intent(v.getContext(), NavigationActivity.class);
    //    startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        EditOrderActivity.this.startActivity(intent);




       // finish();
        //finish();
        // 01.06   Intent myIntent = new Intent(v.getContext(), MainMenu.class);
         finish();


        // Intent myIntent = new Intent(v.getContext(), MainMenu.class);
        //  ClientsActivitySettings.this.startActivityForResult(myIntent, 0);




    }
}
