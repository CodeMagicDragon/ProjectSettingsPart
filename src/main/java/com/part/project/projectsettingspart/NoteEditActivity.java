package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteEditActivity extends AppCompatActivity
{
    EditText note;
    Button noteButton;
    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        getSupportActionBar().hide();
        note = findViewById(R.id.note);
        noteButton = findViewById(R.id.btn_note);
        sPref = (getApplicationContext()).getSharedPreferences("note_data", Context.MODE_PRIVATE);
        setTitle("Заметки");
        loadText();
    }

    //@Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.btn_note)
        {
            saveText();
            finish();
        }
    }

    void saveText()
    {
        SharedPreferences.Editor ed = sPref.edit();
        ed.clear();
        ed.putString("NOTE", note.getText().toString());
        ed.commit();
        ed.apply();
    }

    void loadText()
    {
        //sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString("NOTE", "");
        note.setText(savedText);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
    }
}
