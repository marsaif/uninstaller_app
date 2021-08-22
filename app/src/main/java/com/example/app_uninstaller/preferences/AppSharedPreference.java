package com.example.app_uninstaller.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
    private final String PACKAGE_NAME = "PACKAGE_NAME" ;
    private final String PREF_NAME = "APP" ;
    private final String ITEM_POSITION = "ITEM_POSITION" ;
    private final SharedPreferences sharedPreferences ;
    private static AppSharedPreference appSharedPreference ;

    private AppSharedPreference(Context context)
    {
        sharedPreferences =  context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE) ;
    }

    public static AppSharedPreference getInstance(Context context) {

        if (appSharedPreference == null)
        {
            appSharedPreference = new AppSharedPreference(context) ;
        }
        return appSharedPreference ;
    }

    public void setPackageName(String packageName)
    {
        sharedPreferences.edit().putString(PACKAGE_NAME,packageName).apply();
    }

    public String getPackageName()
    {
       return sharedPreferences.getString(PACKAGE_NAME,"") ;
    }

    public void removePackageName()
    {
        sharedPreferences.edit().remove(PACKAGE_NAME).apply() ;
    }

    public void setItemPosition(int position)
    {
        sharedPreferences.edit().putInt(ITEM_POSITION,position).apply(); ;
    }

    public int getItemPostion()
    {
       return sharedPreferences.getInt(ITEM_POSITION,99) ;
    }

    public void removeItem()
    {
        sharedPreferences.edit().remove(ITEM_POSITION) ;
    }


}

