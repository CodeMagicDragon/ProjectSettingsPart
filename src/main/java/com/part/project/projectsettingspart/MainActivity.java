package com.part.project.projectsettingspart;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    String s;

    FragmentTransaction ft;
    StartFragment startf;
    CardFragment cardf;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startf = new StartFragment();
        cardf = new CardFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_main, startf);
        ft.commit();
    }
}
