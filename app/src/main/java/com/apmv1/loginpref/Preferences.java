package com.apmv1.loginpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;

/**
 * Created by Beto on 03/07/2015.
 */
public class Preferences extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String USERNAME_SETTINGS ="USERNAME_SETTINGS";
    private static final String USERNAME_SETTINGS_DEFAULT = "admin";
    private static final String PASSWORD_SETTINGS = "PASSWORD_SETTINGS";
    private static final String PASSWORD_SETTINGS_DEFAULT ="1234";


    public static class MyPreferenceFragment extends PreferenceFragment
    {

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static String getUsernameSettingsValue(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getString(USERNAME_SETTINGS, USERNAME_SETTINGS_DEFAULT);
    }
    public static String getPasswordSettingsValue(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getString(PASSWORD_SETTINGS, PASSWORD_SETTINGS_DEFAULT);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    private void initSummaries(PreferenceGroup pg) {
        for (int i = 0; i < pg.getPreferenceCount(); ++i) {
            Preference p = pg.getPreference(i);
            if (p instanceof PreferenceGroup)
                this.initSummaries((PreferenceGroup) p); // recursion
            else
                this.setSummary(p);
        }
    }

    private void setSummary(Preference pref) {
        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
        }
        if (pref instanceof EditTextPreference) {
            EditTextPreference editPref = (EditTextPreference) pref;
            pref.setSummary(editPref.getText());
        }
    }
}
