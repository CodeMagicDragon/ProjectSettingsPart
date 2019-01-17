package com.part.project.projectsettingspart;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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

public class SettingsActivity extends AppCompatActivity
{
    ListView settingsList;
    String[] settingsNames = {"Cет для режима", "Блокируемые приложения", "Настройки времени"};
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Настройки режима");
        settingsList = findViewById(R.id.settings_list);
        ArrayAdapter<String> lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingsNames);
        settingsList.setAdapter(lista);
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
                        //startActivity(new Intent(SettingsActivity.this, TimeActivity.class));
                        break;
                    default:
                        break;
                }
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
