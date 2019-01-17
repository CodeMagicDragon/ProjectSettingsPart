package com.part.project.projectsettingspart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.part.project.projectsettingspart.model.SetActions;

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
        setNames = (new SetActions()).loadSetNames();
        ArrayAdapter<String> setAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setNames);
        setList.setAdapter(setAdapter);
        // listeners - go to edit set
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
    }
}
