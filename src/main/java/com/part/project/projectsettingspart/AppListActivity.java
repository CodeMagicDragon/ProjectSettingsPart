package com.part.project.projectsettingspart;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class AppListActivity extends AppCompatActivity
{
    List<Boolean> appEnabled;
    List<String> appPackage;
    String[] strApp;
    PackageManager pm;
    ListView appList;
    boolean a;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        pm = getPackageManager();
        appEnabled = new LinkedList<Boolean>();
        appPackage = new LinkedList<String>();
        (new loadAppList()).execute();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        appList = findViewById(R.id.app_list);
        /*for (ApplicationInfo ai : applist)
        {
            appEnabled.add(ai.packageName);
        }*/
        strApp = new String[appEnabled.size()];
        for (int i = 0; i < appEnabled.size(); i++)
        {
            strApp[i] = appPackage.get(i) + " " + Boolean.toString(appEnabled.get(i));
        }
        ArrayAdapter<String> lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strApp);
        appList.setAdapter(lista);
    }
    private class loadAppList extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            List<ApplicationInfo> applist;
            applist = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo as : applist)
            {
                if ((as.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    appEnabled.add(as.enabled);
                    appPackage.add(pm.getApplicationLabel(as).toString());
                }
            }
            return null;
        }
    }
}
