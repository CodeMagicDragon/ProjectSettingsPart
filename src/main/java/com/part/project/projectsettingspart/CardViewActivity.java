package com.part.project.projectsettingspart;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageView);
        buttonNext = findViewById(R.id.card_button);
        bar = findViewById(R.id.card_bar);
        SharedPreferences sp = (getApplicationContext()).getSharedPreferences("settings", 0);
        //cardSetName = sp.getString("active_set", null);
        //if (cardSetName == null)
        //{
        cardSetName = "base";
        //}
        cards = new Card[cardnum];
        for (int i = 0; i < cardnum; i++)
        {
            cards[i] = (new SetActions()).getRandomCard(App.getInstance().getAppDatabase().getCardDao().getBySetName(cardSetName));
        }
        textView.setText("1/" + Integer.toString(cardnum));
        imageButton.setText(cards[0].firstText);
        imageButton.setOnClickListener(click);
        buttonNext.setOnClickListener(click);
        startAnimation(2000);
        getSupportActionBar().hide();
    }

    private final View.OnClickListener click = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            if (bar.getProgress() == 0)
            {
                if (k >= cardnum * 2 - 1)
                {
                    SharedPreferences sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sp.edit();
                    spEditor.putInt("start_activity", 1);
                    spEditor.apply();
                    finish();
                }
                else
                {
                    k++;
                    textView.setText(Integer.toString(k / 2 + 1) + "/" + Integer.toString(cardnum));
                    if (k % 2 == 1)
                    {
                        imageButton.setText(cards[k / 2].secondText);
                        bar.setVisibility(INVISIBLE);
                        startAnimation(0);
                    }
                    else
                    {
                        imageButton.setText(cards[k / 2].firstText);
                        bar.setVisibility(VISIBLE);
                        startAnimation(2000);
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
}
