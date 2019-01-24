package com.part.project.projectsettingspart;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowNoteActivity extends AppCompatActivity
{
    TextView note;
    ProgressBar progressBar;
    Button nextButton;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    boolean createFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        note = findViewById(R.id.note);
        progressBar = findViewById(R.id.progressBar);
        nextButton = findViewById(R.id.next_btn);
        startAnimation(3000);
        setTitle("Заметка");
        createFlag = true;
        loadText();
        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (progressBar.getProgress() == 0)
                {
                    sp = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                    spEditor = sp.edit();
                    spEditor.putBoolean("start_activity", false);
                    spEditor.putBoolean("test_passed", true);
                    spEditor.apply();
                    finish();
                }
            }
        });
    }

    void loadText()
    {
        sp = (getApplicationContext()).getSharedPreferences("note_data", Context.MODE_PRIVATE);
        String savedText = sp.getString("NOTE", "");
        note.setText(savedText);
    }

    private void startAnimation(int duration)
    {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 100, 0);
        progressBar.setScaleY(15f);
        progressAnimator.setDuration(duration);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!createFlag)
        {
            finish();
        }
        createFlag = false;
    }
}
