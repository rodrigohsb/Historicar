package com.historicar.app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rodrigo on 30/01/16.
 */
public class PreferenceUtils
{

    private static final String DEFAULT_STRING_VALUE = "";
    private static final int DEFAULT_INT_VALUE = 0;

    private static Context mContext;

    private static PreferenceUtils mInstance;

    public static PreferenceUtils getInstance (Context context)
    {
        if (mInstance == null)
        {
            mInstance = new PreferenceUtils(context);
        }
        return mInstance;
    }

    private PreferenceUtils (Context context)
    {
        mContext = context;
    }

    private static SharedPreferences getSharedPreferences (Context context)
    {
        return context.getApplicationContext().getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public int recoverInt (String key)
    {
        return getSharedPreferences(mContext).getInt(key, DEFAULT_INT_VALUE);
    }

    public void persist (String key, int value)
    {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String recoverString (String key)
    {
        return getSharedPreferences(mContext).getString(key, DEFAULT_STRING_VALUE);
    }

    public void persist (String key, String value)
    {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void delete (String key)
    {
        SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.remove(key);
        editor.apply();
    }
}
