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
    Card[] cards;
    String cardSetName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Log.d("THIS", "  started");
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
        cardSetName = sp.getString("active_set", "base");
        cards = new Card[cardNum];
        for (int i = 0; i < cardNum; i++)
        {
            cards[i] = (new SetActions()).getRandomCard(App.getInstance().getAppDatabase().getCardDao().getBySetName(cardSetName));
        }
        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (ind + 1 == cardNum)
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
        //Log.d("THIS", "  show 1");
        next = 0;
        showed += 1;
        cardButton.setText(cards[ind].firstText);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nextButton.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void showTranslate(int ind)
    {
        Log.d("THIS", "");
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
            finish();
        }
        createFlag = false;
    }
}


/*import static android.view.View.INVISIBLE;
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
                        else
                        {
                            finish();
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
}
*/