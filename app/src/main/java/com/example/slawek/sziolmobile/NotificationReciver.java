package com.example.slawek.sziolmobile;

/**
 * Created by Michał on 2015-05-10.
 */


        import android.app.Activity;
        import android.content.Intent;
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
    protected void onResume()
    {
        super.onResume();
        LoadNotification();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        finish();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_notification);

    }

    private void LoadNotification()
    {
        try {

            nm = (NotificationModel) getIntent().getExtras().getSerializable("notification");

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


            } catch (JSONException e) {
            }

            order = new Order(id, title, description, status, Integer.parseInt(customerId), executorId, teamId, date, creatorId);
            String aktualnyStatus = GetStatus(status);

            tv = (TextView) findViewById(R.id.TV_notification);
            tv.setText("Tytuł: " + title.toString()+ "\n" + "Opis: " + description.toString() + "\n" + "Status: " + aktualnyStatus );//+ date.toString());
        }catch (Exception ex)
        {

        }
    }

    private String GetStatus(String status)
    {
        if(status.toString().equalsIgnoreCase("CR"))
        {
            return "Gotowe do realizacji";
        }
        if(status.toString().equalsIgnoreCase("AS"))
        {
            return "Przypisane";
        }
        if(status.toString().equalsIgnoreCase("EX"))
        {
            return "Realizowane";
        }
        if(status.toString().equalsIgnoreCase("CL"))
        {
           return  "Wykonano";
        }

        return "";
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
                status = restService.SendStatusNotification(Integer.parseInt(nm.getId()), true);
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
        Intent myIntent = new Intent(v.getContext(), NavigationActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // 01.06   Intent myIntent = new Intent(v.getContext(), MainMenu.class);
        NotificationReciver.this.startActivity(myIntent);
        finish();
    //       finish();
    }


    public void notAcceptNotificationOnClick(View v)
    {
        RestClientService restClientService = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        RestService restService = new RestService(restClientService);
        restService.SendStatusNotification(Integer.parseInt(nm.getId()), false);
         finish();
        //????
    }
}
