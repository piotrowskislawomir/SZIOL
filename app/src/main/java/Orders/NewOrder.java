package Orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

import com.example.slawek.sziolmobile.MainMenu;
import com.example.slawek.sziolmobile.R;

import Models.Client;
import sziolmobile.RestClientService;
import sziolmobile.RestService;

/**
 * Created by Michał on 2015-04-14.
 */
public class NewOrder extends Activity {
    private EditText firstNameClient;
    private EditText lastNameClient;
    private EditText addressClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_client);
        firstNameClient = (EditText)findViewById(R.id.ET_new_client_name);
        lastNameClient = (EditText)findViewById(R.id.ET_new_client_last_name);
      //  addressClient = (EditText)findViewById(R.id.ET_new_client_address);
    }


    public void addNewClientButtonOnClick(View v) {


       // final Client client = new Client(firstNameClient.getText().toString(), lastNameClient.getText().toString(), addressClient.getText().toString());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
     //           restService.AddNewCustomer(client);
                //  TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });

        Intent intent = new Intent(NewOrder.this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
