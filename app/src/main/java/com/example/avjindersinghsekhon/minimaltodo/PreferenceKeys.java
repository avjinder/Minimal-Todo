package com.example.avjindersinghsekhon.minimaltodo;

import android.content.res.Resources;

/**
 * Created by avjindersinghsekhon on 9/21/15.
 */
public class PreferenceKeys {
    final String night_mode_pref_key;

    public PreferenceKeys(Resources resources){
        night_mode_pref_key = resources.getString(R.string.night_mode_pref_key);
    }
}
