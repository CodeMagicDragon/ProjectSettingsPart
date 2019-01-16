package com.part.project.projectsettingspart;

import android.app.Application;
import android.arch.persistence.room.Room;

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
        Card card = new Card();
        for (int i = 0; i < baseCardNum; i++)
        {
            card.name = "name" + Integer.toString(i);
            card.firstText = firstTexts[i];
            card.secondText = secondTexts[i];
            card.setName = "base";
            appDatabase.getCardDao().insert(card);
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
