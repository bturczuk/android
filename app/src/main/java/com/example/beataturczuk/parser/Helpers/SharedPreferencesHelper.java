package com.example.beataturczuk.parser.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by beataturczuk on 16.03.15.
 */
public class SharedPreferencesHelper {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String etResponse = "etResponsePrefs";


    public SharedPreferencesHelper(Context mContext) {
    this.mSharedPreferences = mContext.getSharedPreferences(CommandData.USER_PREFS, Context.MODE_PRIVATE);
    this.mEditor = mSharedPreferences.edit();
    }


    public String getEtResponse() {
        return mSharedPreferences.getString(etResponse, "unknown");
    }

    public void setEtResponse(String etResponse1) {
        mEditor.putString(etResponse, etResponse1).apply();
    }
}
