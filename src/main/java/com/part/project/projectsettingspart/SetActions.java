package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SetActions
{
    SetActions ()
    {

    }

    public String[] loadSetNames(Context context)
    {
        String[] setNames;
        SharedPreferences sp = context.getSharedPreferences("sets", Context.MODE_PRIVATE);
        if (sp.contains("set_names"))
        {
            Set<String> nameSet = new HashSet<>();
            sp.getStringSet("set_names", nameSet);
            setNames = new String[nameSet.size()];
            int i = 0;
            for (String s : nameSet)
            {
                setNames[i] = s;
                i++;
            }
        }
        else
        {
            setNames = new String[0];
        }
        return setNames;
    }
}
