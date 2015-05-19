package com.example.slawek.sziolmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Michał on 2015-05-10.
 */
public class Alert extends Activity {

    public void Alert(String str)
    {



        new AlertDialog.Builder(this)
                .setTitle("Komunikat")
                .setMessage(str)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                        // .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        //     public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        //       }
                        // })
                        // .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
