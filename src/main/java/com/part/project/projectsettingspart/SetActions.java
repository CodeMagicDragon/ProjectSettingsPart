package com.part.project.projectsettingspart;

import com.part.project.projectsettingspart.model.AppDatabase;
import com.part.project.projectsettingspart.model.Card;
import com.part.project.projectsettingspart.model.CardDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetActions
{
    AppDatabase database;

    public String[] loadSetNames()
    {
        String[] setNames;
        database = App.getInstance().getAppDatabase();
        CardDao cardDao = database.getCardDao();
        String[] setNamesList = cardDao.getAllSetNames();
        Set<String> setNamesSet = new HashSet<>();
        for (int i = 0; i < setNamesList.length; i++)
        {
            if (!setNamesSet.contains(setNamesList[i]))
            {
                setNamesSet.add(setNamesList[i]);
            }
        }
        setNames = new String[setNamesSet.size()];
        int i = 0;
        for (String s : setNamesSet)
        {
            setNames[i] = s;
            i++;
        }
        return setNames;
    }
    public Card CardBuilder(int id, String setName, String name, String firstText, String secondText)
    {
        Card c = new Card();
        c.id = id;
        c.setName = setName;
        c.name = name;
        c.firstText = firstText;
        c.secondText = secondText;
        return c;
    }
    public Card getRandomCard(Card[] set)
    {
        if (set == null || set.length == 0)
        {
            return CardBuilder(0, "null", "null", "null", "null");
        }
        return set[(int)(Math.random() * set.length)];
    }
}
