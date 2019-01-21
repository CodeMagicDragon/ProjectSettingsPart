package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener
{
    Intent intent;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    boolean[] blockOptions;
    int checkboxSettingsLength = 3;
    boolean checkboxOption;
    int checkboxPosition;

    @Override
    public void onCreatePreferences(Bundle bundle, String s)
    {
        addPreferencesFromResource(R.xml.main_settings);
        checkboxOption = false;
        checkboxPosition = 0;
        sp = (getActivity().getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
        spEditor = sp.edit();
        blockOptions = new boolean[checkboxSettingsLength];
        // set ok
        for (int i = 0; i < blockOptions.length; i++)
        {
            blockOptions[i] = sp.getBoolean("block_option_" + Integer.toString(i), false);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        checkboxOption = false;
        switch(key)
        {
            case "set_pref":
                intent = new Intent(getActivity(), SetChooseActivity.class);
                startActivity(intent);
                break;
            case "block_pref":
                intent = new Intent(getActivity(), LoadActivity.class);
                startActivity(intent);
                break;
            case "note_pref":
                intent = new Intent(getActivity(), NoteEditActivity.class);
                startActivity(intent);
                break;
            case "full_block_pref":
                checkboxOption = true;
                checkboxPosition = 0;
                break;
            case "show_cards_pref":
                checkboxOption = true;
                checkboxPosition = 1;
                break;
            case "show_note_pref":
                checkboxOption = true;
                checkboxPosition = 2;
                break;
            default:
                break;
        }
        if (checkboxOption)
        {
            blockOptions[checkboxPosition] = !blockOptions[checkboxPosition];
            spEditor.putBoolean("block_option_" + Integer.toString(checkboxPosition), blockOptions[checkboxPosition]);
            spEditor.apply();
        }
    }
}
