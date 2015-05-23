package com.example.slawek.sziolmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

/**
 * Created by Slawek on 2015-05-23.
 */
public class SharedPropertiesManager {

    Context _ctx;
    SharedPreferences _sharedPreferences;

    public SharedPropertiesManager(Context ctx)
    {
        _ctx = ctx;
        Initialize();
    }

    private void Initialize()
    {
        Resources res = _ctx.getResources();
        _sharedPreferences = _ctx.getSharedPreferences(res.getString(R.string.shared_space),Context.MODE_PRIVATE);
    }

    public void SetValue(String key, String value)
    {
        _sharedPreferences.edit().putString(key, value).apply();
    }

    public String GetValue(String key, String defaultValue)
    {
        return _sharedPreferences.getString(key, defaultValue);
    }
}
