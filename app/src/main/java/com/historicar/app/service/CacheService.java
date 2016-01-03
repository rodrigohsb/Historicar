package com.historicar.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.historicar.app.bean.Multa;
import com.historicar.app.contants.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rodrigo on 03/01/16.
 */
public class CacheService extends IntentService
{

    private static final String TAG = "CacheService";

    public CacheService ()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent (Intent intent)
    {
        try
        {
            String json = intent.getExtras().getString(Constants.MULTAS);
            String placa = intent.getExtras().getString(Constants.PLACA_KEY);

            List<Multa> newMultas = Arrays.asList(new Gson().fromJson(json, Multa[].class));

            List<Multa> oldMultas = recoverFromCache(placa);

            if (newMultas.size() != oldMultas.size())
            {
                saveInCache(placa, json);
            }
            else
            {
                Log.i(TAG, "Conteudo ja existe!");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void saveInCache(String placa, String json)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(placa, json).apply();

        Log.i(TAG, "Cache armazenado com sucesso!");
    }

    private List<Multa> recoverFromCache(String placa)
    {
        String json = PreferenceManager.getDefaultSharedPreferences(this).getString(placa, null);

        if(json != null)
        {
            return Arrays.asList(new Gson().fromJson(json, Multa[].class));
        }
        return new ArrayList<>();
    }
}
