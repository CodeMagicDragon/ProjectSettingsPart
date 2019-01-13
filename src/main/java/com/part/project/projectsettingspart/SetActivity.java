package com.part.project.projectsettingspart;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SetActivity extends AppCompatActivity
{
    FragmentTransaction ft;
    ListFragment listf;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setTitle("Сеты");
        listf = new ListFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_set, listf);
        ft.commit();
    }
}
