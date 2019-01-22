package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;

import com.part.project.projectsettingspart.model.Card;
import com.part.project.projectsettingspart.model.SetActions;

public class CardViewActivity extends AppCompatActivity
{
    int cardNum = 3;
    int barSpeed = 7;
    ProgressBar progressBar;
    Button nextButton, cardButton;
    int next = 1;
    int ind = 0;
    int showed = 0;
    int last = 0;
    int stop = 1;
    Animation animTranslate, animTranslate2;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    boolean createFlag;
    boolean fullSetMode;
    Card[] cards;
    String cardSetName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        getSupportActionBar().hide();
        progressBar = findViewById(R.id.progressBar);
        cardButton = findViewById(R.id.cardButton);
        nextButton = findViewById(R.id.nextButton);
        progressBar.setProgress(0);
        progressBar.setMax(300);
        progressBar.setScaleY(15f);
        animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        animTranslate2 = AnimationUtils.loadAnimation(this, R.anim.anim_translate2);
        sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
        spEditor = sp.edit();
        createFlag = true;
        fullSetMode = sp.getBoolean("full_set_mode", false);
        cardSetName = sp.getString("active_set", "base");
        if (!fullSetMode)
        {
            cards = new Card[cardNum];
            for (int i = 0; i < cardNum; i++)
            {
                cards[i] = (new SetActions()).getRandomCard(App.getInstance().getAppDatabase().getCardDao().getBySetName(cardSetName));
            }
        }
        else
        {
            cards = App.getInstance().getAppDatabase().getCardDao().getBySetName(cardSetName);
            cardNum = cards.length;
        }
        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (ind + 1 == cardNum)
                {
                    if (!fullSetMode)
                    {
                        spEditor.putInt("start_activity", 1);
                        spEditor.apply();
                    }
                    if (sp.getBoolean("block_option_2", false) && !fullSetMode)
                    {
                        Intent intent = new Intent();
                        intent.setClass(CardViewActivity.this, ShowNoteActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else
                    {
                        finish();
                    }
                    return;
                }
                else
                {
                    progressBar.setProgress(0);
                    next = 1;
                    ind += 1;
                    last = 0;
                    anim_translate(cards[ind - 1].secondText, cards[ind].firstText);
                    showCard(ind);
                }
            }
        });
        showCard(ind);
    }


    private void anim_translate(String oldText, String newText)
    {
        final String t1 = oldText;
        final String t2 = newText;
        cardButton.startAnimation(animTranslate);
        animTranslate.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                cardButton.setText(t1);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                cardButton.startAnimation(animTranslate2);
                cardButton.setText(t2);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });

    }

    public void showCard(int ind)
    {
        next = 0;
        showed += 1;
        cardButton.setText(cards[ind].firstText);
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void showTranslate(int ind)
    {
        cardButton.setText(cards[ind].secondText);
    }

    private class LongOperation extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            while (true)
            {
                if (progressBar.getProgress() >= progressBar.getMax())
                {
                    showTranslate(ind);
                    nextButton.setText("Next");
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            nextButton.setVisibility(View.VISIBLE);
                        }
                    });
                    //showCard(ind);
                    LO.cancel(true);
                }

                if (stop == 1)
                {
                    stop = 1;
                    return "Executed";
                }
                progressBar.setProgress(last + barSpeed);
                last += barSpeed;
                try
                {
                    Thread.sleep(10);
                } catch (InterruptedException e)
                {
                    Thread.interrupted();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            LO.cancel(true);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }



    LongOperation LO;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        //Log.d("PRESS", "touch");

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: // нажатие
                stop = 0;
                LO = new LongOperation();
                LO.execute("");
                //Log.d("PRESS", "press");
                break;
            case MotionEvent.ACTION_MOVE: // движение
                //Log.d("PRESS", "move");
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                LO.onPostExecute("");
                stop = 1;
                //Log.d("PRESS", "free");
                break;
        }
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!createFlag)
        {
            if (!fullSetMode)
            {
                finish();
            }
        }
        createFlag = false;
    }
}