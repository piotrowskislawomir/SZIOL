package services.databases;

import android.provider.BaseColumns;

/**
 * Created by Slawek on 2015-07-15.
 */
public class DatabaseContract {

    public DatabaseContract() {
    }

    public static abstract class ConfigurationEntry implements BaseColumns {
        public static final String TABLE_NAME = "Configurations";
        public static final String COLUMN_KEY = "Key";
        public static final String COLUMN_VALUE = "Value";
    }
}