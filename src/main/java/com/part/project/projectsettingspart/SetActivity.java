package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashSet;
import java.util.ListResourceBundle;
import java.util.Set;

public class SetActivity extends AppCompatActivity
{
    ListView setList;
    Button buttonAdd;
    String[] setNames;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setTitle("Сеты");
        setList = findViewById(R.id.set_edit_list);
        buttonAdd = findViewById(R.id.set_add_button);
        SharedPreferences sp = getSharedPreferences("sets", Context.MODE_PRIVATE);
        setNames = (new SetActions()).loadSetNames(this);
        ArrayAdapter<String> setAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setNames);
        setList.setAdapter(setAdapter);
        // listeners - go to edit set
    }
}
