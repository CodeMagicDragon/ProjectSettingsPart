package com.part.project.projectsettingspart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    String s;

    Button bDownload, bEdit, bSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bDownload = findViewById(R.id.b_download);
        bEdit = findViewById(R.id.b_edit);
        bSettings = findViewById(R.id.b_settings);
        bDownload.setOnClickListener(click);
        bEdit.setOnClickListener(click);
        bSettings.setOnClickListener(click);
    }

    private final View.OnClickListener click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.b_settings:
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    // go to settings
                    break;
                case R.id.b_edit:
                    startActivity(new Intent(MainActivity.this, SetActivity.class));
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
