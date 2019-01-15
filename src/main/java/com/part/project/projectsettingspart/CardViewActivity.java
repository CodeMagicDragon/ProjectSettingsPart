package com.part.project.projectsettingspart;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class CardViewActivity extends AppCompatActivity
{
    ProgressBar bar;
    Button imageButton;
    Button buttonNext;
    TextView textView;
    int k = 0;
    String[] cards;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageView);
        buttonNext = findViewById(R.id.card_button);
        bar = findViewById(R.id.card_bar);
        cards = new String[]{"cat", "кошка", "dog", "собака", "whale", "кит"};
        textView.setText("1/" + Integer.toString(cards.length / 2));
        imageButton.setText("Cat");
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
                if (k >= cards.length - 1)
                {
                    finish();
                }
                else
                {
                    k++;
                    imageButton.setText(cards[k]);
                    textView.setText(Integer.toString(k / 2 + 1) + "/" + Integer.toString(cards.length / 2));
                    if (k % 2 == 1)
                    {
                        bar.setVisibility(INVISIBLE);
                        startAnimation(0);
                    }
                    else
                    {
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
