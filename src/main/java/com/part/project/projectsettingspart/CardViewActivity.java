package com.part.project.projectsettingspart;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.part.project.projectsettingspart.model.Card;
import com.part.project.projectsettingspart.model.SetActions;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class CardViewActivity extends AppCompatActivity
{
    ProgressBar bar;
    Button imageButton;
    Button buttonNext;
    TextView textView;
    String cardSetName;
    int cardnum = 3;
    int k = 0;
    Card[] cards;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    int clickIterator;
    boolean createFlag;
    TextView textClick;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageView);
        buttonNext = findViewById(R.id.card_button);
        bar = findViewById(R.id.card_bar);
        textClick = findViewById(R.id.text_click);
        sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
        spEditor = sp.edit();
        createFlag = true;
        clickIterator = sp.getInt("click_num", 0);
        cardSetName = sp.getString("active_set", "base");
        cards = new Card[cardnum];
        for (int i = 0; i < cardnum; i++)
        {
            cards[i] = (new SetActions()).getRandomCard(App.getInstance().getAppDatabase().getCardDao().getBySetName(cardSetName));
        }
        textView.setText("1/" + Integer.toString(cardnum));
        textClick.setText("Кликов: " + clickIterator);
        imageButton.setText(cards[0].firstText);
        imageButton.setOnClickListener(click);
        buttonNext.setOnClickListener(click);
        buttonNext.setVisibility(INVISIBLE);
        startAnimation(2000);
        getSupportActionBar().hide();
    }

    private final View.OnClickListener click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if ((k % 2 == 0 && v.getId() == R.id.imageView) || (k % 2 == 1 && v.getId() == R.id.card_button))
            {
                if (bar.getProgress() == 0)
                {
                    k++;
                    clickIterator++;
                    spEditor.putInt("click_num", clickIterator);
                    spEditor.apply();
                    if (k >= 2 * cardnum)
                    {
                        spEditor.putInt("start_activity", 1);
                        spEditor.apply();
                        if (sp.getBoolean("block_option_2", false))
                        {
                            Intent intent = new Intent();
                            intent.setClass(CardViewActivity.this, ShowNoteActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        textView.setText(Integer.toString(k / 2 + 1) + "/" + Integer.toString(cardnum));
                        textClick.setText("Кликов: " + clickIterator);
                        if (k % 2 == 1)
                        {
                            imageButton.setText(cards[k / 2].secondText);
                            imageButton.setClickable(false);
                            bar.setVisibility(INVISIBLE);
                            buttonNext.setVisibility(VISIBLE);
                            startAnimation(0);
                        }
                        else
                        {
                            imageButton.setText(cards[k / 2].firstText);
                            imageButton.setClickable(true);
                            bar.setVisibility(VISIBLE);
                            buttonNext.setVisibility(INVISIBLE);
                            startAnimation(2000);
                        }
                    }
                }
            }
        }
    };

    private void startAnimation(int duration)
    {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(bar, "progress", 100, 0);
        bar.setScaleY(4f);
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
