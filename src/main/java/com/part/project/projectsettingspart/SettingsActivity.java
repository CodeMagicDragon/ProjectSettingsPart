package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;
import java.util.prefs.PreferenceChangeListener;

public class SettingsActivity extends AppCompatActivity
{
    ListView settingsList;
    ListView blockSettingsList;
    String[] settingsNames = {"Cет для режима", "Блокируемые приложения", "Заметки"};
    String[] blockSettingsNames = {"Полная блокировка", "Показывать карточки", "Показывать заметки", "Карточки с проверкой"};
    boolean[] blockOptions;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        settingsList = findViewById(R.id.settings_list);
        blockSettingsList = findViewById(R.id.block_settings_list);
        sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
        spEditor = sp.edit();
        blockOptions = new boolean[blockSettingsNames.length];
        for (int i = 0; i < blockOptions.length; i++)
        {
            blockOptions[i] = sp.getBoolean("block_option_" + Integer.toString(i), false);
        }
        // load blockSettingsList
        blockSettingsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, blockSettingsNames));
        settingsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingsNames));
        for(int i = 0; i < blockOptions.length; i++)
        {
            blockSettingsList.setItemChecked(i, blockOptions[i]);
        }
        setTitle("Настройки");
        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id)
            {
                switch (p)
                {
                    case 0:
                        intent = new Intent(SettingsActivity.this, SetChooseActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(SettingsActivity.this, LoadActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(SettingsActivity.this, NoteEditActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        blockSettingsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id)
            {
                blockOptions[p] = !blockOptions[p];
                spEditor.putBoolean("block_option_" + Integer.toString(p), blockOptions[p]);
                spEditor.apply();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
    }
}

/*public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    // @Override
    // public void onBuildHeaders(List<Header> target)
    // {
    //     loadHeadersFromResource(R.xml.main_settings_headers, target);
    // }

    // @Override
    // protected boolean isValidFragment(String fragmentName)
    // {
    //    return SettingsFragment.class.getName().equals(fragmentName);
    // }
}*/