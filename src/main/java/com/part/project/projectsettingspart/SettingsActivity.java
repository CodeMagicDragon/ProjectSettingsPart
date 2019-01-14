package com.part.project.projectsettingspart;

import android.content.Intent;
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

public class SettingsActivity extends AppCompatActivity
{
    ListView settingsList;
    String[] settingsNames = {"Настройки времени", "Cет для режима", "Блокируемые приложения"};

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
                        //startActivity(new Intent(SettingsActivity.this, TimeActivity.class));
                        break;
                    case 1:
                        //startActivity(new Intent(SettingsActivity.this, SetsActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(SettingsActivity.this, AppListActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
