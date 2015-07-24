package services.databases;

import android.provider.BaseColumns;

/**
 * Created by Slawek on 2015-07-15.
 */
public class ConfigurationsContract {

    public ConfigurationsContract() {
    }

    public static abstract class ConfigurationEntry implements BaseColumns {
        public static final String TABLE_NAME = "Configurations";
        public static final String COLUMN_NAME_ID = "Key";
        public static final String COLUMN_NAME_TITLE = "Value";
    }
}