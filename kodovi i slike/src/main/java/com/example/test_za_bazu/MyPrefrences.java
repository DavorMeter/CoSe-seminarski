package com.example.test_za_bazu;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPrefrences {
    String PREFS_NAME = "MyPreferences";
    SharedPreferences sharedPreferencesInstance;
    public void MyPreferences (Context context) {
        sharedPreferencesInstance=context.getSharedPreferences(PREFS_NAME, 0);
    }
    public void setString (String key, String value) {
        sharedPreferencesInstance.edit().putString(key,value).commit();
    }
    public void setInt (String key, Integer value) {
        sharedPreferencesInstance.edit().putInt(key,value).commit();
    }
    public String getString (String key)
    {
        return sharedPreferencesInstance.getString(key,"");
    }

    public Integer getInt(String key) {
        return sharedPreferencesInstance.getInt(key, 0);
    }
}
