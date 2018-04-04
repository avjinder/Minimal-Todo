package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.content.res.Resources;

import com.example.avjindersinghsekhon.minimaltodo.R;

/**
 * Created by avjindersinghsekhon on 9/21/15.
 */
public class PreferenceKeys {
    public final String night_mode_pref_key;

    public PreferenceKeys(Resources resources) {
        night_mode_pref_key = resources.getString(R.string.night_mode_pref_key);
    }
}
