package com.part.project.projectsettingspart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CardSet
{
    String setName;
    Card[] cardSet;
    CardSet(Card[] cardSet, String setName)
    {
        this.cardSet = cardSet;
        this.setName = setName;
    }
    public Card getRandomCard()
    {
        int x = (int)(Math.random() * cardSet.length);
        return new Card(cardSet[x].getName(), cardSet[x].getFirstText(), cardSet[x].getSecondText());
    }
    public boolean loadSet(String file)
    {
        return false;
    }
    public boolean saveSet(String file)
    {
        return false;
    }
}
