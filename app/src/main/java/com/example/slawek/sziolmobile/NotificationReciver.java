package com.example.slawek.sziolmobile;

/**
 * Created by Michał on 2015-05-10.
 */


        import android.app.Activity;
        import android.os.Bundle;
        import android.widget.TextView;

public class NotificationReciver extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = new TextView(this);
        tv.setText("Yo!");



        setContentView(tv);
    }
}
