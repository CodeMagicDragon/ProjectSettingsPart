package com.part.project.projectsettingspart;

class Card
{
    private String name;
    private String firstText;
    private String secondText;
    Card (String name, String firstText, String secondText)
    {
        this.name = name;
        this.firstText = firstText;
        this.secondText = secondText;
    }

    public String getName()
    {
        return name;
    }

    public String getFirstText()
    {
        return firstText;
    }

    public String getSecondText()
    {
        return secondText;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setFirstText(String firstText)
    {
        this.firstText = firstText;
    }

    public void setSecondText(String secondText)
    {
        this.secondText = secondText;
    }

    public boolean equals(Card obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj.getName() == name && obj.getFirstText() == firstText && obj.getSecondText() == secondText)
        {
            return true;
        }
        return false;
    }
}
