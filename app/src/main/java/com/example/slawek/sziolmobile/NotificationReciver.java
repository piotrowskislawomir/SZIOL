package com.example.slawek.sziolmobile;

/**
 * Created by Michał on 2015-05-10.
 */


        import android.app.Activity;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONException;
        import org.json.JSONObject;

        import Models.NotificationModel;
        import sziolmobile.RestClientService;
        import sziolmobile.RestService;

public class NotificationReciver extends Activity {

    NotificationModel nm;
    JSONObject jsonObj;
    String id, description, status, date, creatorId, executorId, customerId, teamId, title;
    Order order;
    TextView tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_notification);

        //
           nm = NotificationService.getNotification();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                restService.GetOrder(Integer.parseInt(nm.getTicketId()));
            }
        });

        try {
            // singleOrder = new JSONArray(RestClientService.resp);
            jsonObj = new JSONObject(RestClientService.resp);
            id = jsonObj.get("Id").toString();
            title = jsonObj.get("Title").toString();
            description = jsonObj.get("Description").toString();
            status = jsonObj.get("Status").toString();
            date = jsonObj.get("CreateDate").toString();
            creatorId = jsonObj.get("CreatorId").toString();
            executorId = jsonObj.get("ExecutorId").toString();
            customerId = jsonObj.get("CustomerId").toString();
            teamId = jsonObj.get("TeamId").toString();


        }
        catch(JSONException e){}

        // order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, true);

        order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, teamId, date, creatorId );



        tv = (TextView) findViewById(R.id.TV_notification);
        tv.setText(title.toString() + "\n" + description.toString() + "\n" + status.toString() + "\n" + date.toString());


        //

     //   TextView tv = new TextView(this);
     //   tv.setText("Yo!");
     // setContentView(tv);
    }


    public void acceptNotificationOnClick(View v)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        runOnUiThread(new Runnable() {
            public void run() {
                RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
                RestService restService = new RestService(restClientService);
                int status =  restService.PinOrder(Integer.parseInt(id), order);
                status = restService.SendStatusNotification(Integer.parseInt(nm.getNotificationId()),order.getId(), true);
                if(status == 200)
                {
                    Toast.makeText(getApplicationContext(), "przypięcie ok", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "przypięcie NIE ok", Toast.LENGTH_LONG).show();
                }

                //  TextView tv = (TextView) findViewById(R.id.textView3);

            }
        });
           finish();
    }


    public void notAcceptNotificationOnClick(View v)
    {
        RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        RestService restService = new RestService(restClientService);
        restService.SendStatusNotification(Integer.parseInt(nm.getNotificationId()), order.getId(), false);
         finish();
        //????
    }
}
