package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.part.project.projectsettingspart.model.SetActions;

public class SetChooseActivity extends AppCompatActivity
{
    ListView setList;
    String[] setNames;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_choose);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        setList = findViewById(R.id.set_choose_list);
        setNames = (new SetActions()).loadSetNames();
        ArrayAdapter<String> setAdapter = new ArrayAdapter<String>(this, R.layout.app_list_item, setNames);
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
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
    }
}
