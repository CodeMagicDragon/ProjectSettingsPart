package com.part.project.projectsettingspart;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.part.project.projectsettingspart.model.SetActions;

public class SetActivity extends AppCompatActivity
{
    ListView setList;
    Button buttonAdd;
    String[] setNames;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        setTitle("Сеты");
        setList = findViewById(R.id.set_edit_list);
        buttonAdd = findViewById(R.id.set_add_button);
        setList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id)
            {
                intent = new Intent(SetActivity.this, CardListEditActivity.class);
                intent.putExtra("set_name", setNames[p]);
                startActivity(intent);
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(SetActivity.this, CardListEditActivity.class);
                intent.putExtra("set_name", "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
        setNames = (new SetActions()).loadSetNames();
        ArrayAdapter<String> setAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setNames);
        setList.setAdapter(setAdapter);
    }
}
