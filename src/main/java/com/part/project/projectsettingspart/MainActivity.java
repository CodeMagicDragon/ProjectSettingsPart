package com.part.project.projectsettingspart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    String s;

    FragmentTransaction ft;
    StartFragment startf;
    ListFragment listf;
    SettingsFragment settingsf;
    Button bDownload, bEdit, bSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startf = new StartFragment();
        listf = new ListFragment();
        settingsf = new SettingsFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_main, startf);
        bDownload = findViewById(R.id.b_download);
        bEdit = findViewById(R.id.b_edit);
        bSettings = findViewById(R.id.b_settings);
        bDownload.setOnClickListener(click);
        bEdit.setOnClickListener(click);
        bSettings.setOnClickListener(click);
        ft.commit();
    }

    private void replaceFragment(Fragment f)
    {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_main, f);
        ft.commit();
    }

    private final View.OnClickListener click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.b_settings:
                    replaceFragment(settingsf);
                    // go to settings
                    break;
                case R.id.b_edit:
                    replaceFragment(listf);
                    // go to card set list
                    break;
                case R.id.b_download:
                    // go to web part
                    break;
                default:
                    break;
            }
        }
    };
}
