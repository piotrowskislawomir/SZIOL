package services.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Slawek on 2015-07-15.
 */
public class ConfigurationsDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Tickets.db";


    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TICKETS =
            "CREATE TABLE IF NOT EXISTS " + ConfigurationsContract.TicketEntry.TABLE_NAME + " (" +
                    ConfigurationsContract.TicketEntry.ID + " INTEGER PRIMARY KEY," +
                    ConfigurationsContract.TicketEntry.COLUMN_NAME_ID + INT_TYPE + COMMA_SEP +
                    ConfigurationsContract.TicketEntry.COLUMN_NAME_TITLE + TEXT_TYPE+ ")";

    private static final String SQL_DELETE_TICKETS =
            "DROP TABLE IF EXISTS " + ConfigurationsContract.TicketEntry.TABLE_NAME;

        public ConfigurationsDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TICKETS);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_TICKETS);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
