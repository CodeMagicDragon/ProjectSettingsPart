package com.part.project.projectsettingspart;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.part.project.projectsettingspart.model.AppDatabase;
import com.part.project.projectsettingspart.model.Card;

public class App extends Application
{
    public static App instance;

    private AppDatabase appDatabase;

    int baseCardNum = 10;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "database").allowMainThreadQueries().build();
        Card[] card = new Card[baseCardNum];
        for (int i = 0; i < baseCardNum; i++)
        {
            card[i].id = i;
            card[i].name = "name " + Integer.toString(i);
            card[i].firstText = "cat";
            card[i].secondText = "кошка";
            card[i].setName = "base";
        }
        for (int i = 0; i < card.length; i++)
        {
            appDatabase.getCardDao().update(card[i]);
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
