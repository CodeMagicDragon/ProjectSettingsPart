package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SetChooseActivity extends AppCompatActivity
{
    ListView setList;
    String[] setNames;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_choose);
        setList = findViewById(R.id.set_choose_list);
        setNames = (new SetActions()).loadSetNames();
        ArrayAdapter<String> setAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setNames);
        setList.setAdapter(setAdapter);
        setList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id)
            {
                SharedPreferences sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sp.edit();
                spEditor.putString("active_set", setNames[p]);
                spEditor.apply();
                finish();
            }
        });
        // set click listener
    }
}
