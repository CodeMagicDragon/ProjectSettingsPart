package com.part.project.projectsettingspart;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.part.project.projectsettingspart.model.AppDatabase;
import com.part.project.projectsettingspart.model.Card;

public class App extends Application
{
    public static App instance;

    private AppDatabase appDatabase;

    int baseCardNum = 12;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "cards_database").allowMainThreadQueries().build();
        String[] firstTexts = new String[]{"cat", "dog", "whale", "bird", "elephant", "monkey", "mouse", "human", "tiger", "horse", "lion", "bear"};
        String[] secondTexts = new String[]{"кошка", "собака", "кит", "птица", "слон", "обезьяна", "мышь", "человек", "тигр", "лошадь", "лев", "медведь"};
        //Card[] card = new Card[baseCardNum];
        SharedPreferences sp = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        if (!sp.contains("first_launch"))
        {
            Card card = new Card();
            for (int i = 0; i < baseCardNum; i++) {
                card.name = "name" + Integer.toString(i);
                card.firstText = firstTexts[i];
                card.secondText = secondTexts[i];
                card.setName = "base";
                appDatabase.getCardDao().insert(card);
            }
            spEditor.putInt("first_launch", 0);
            spEditor.apply();
        }
    }

    public void destroyActivityOnResume(Activity activity)
    {
        SharedPreferences sp  = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (sp.contains("start_activity") && sp.getInt("start_activity", 0) == 1)
        {
            activity.finish();
        }
    }

    public static App getInstance()
    {
        return instance;
    }

    public AppDatabase getAppDatabase()
    {
        return appDatabase;
    }
}
