package com.part.project.projectsettingspart;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class AppListActivity extends AppCompatActivity
{
    //List<Boolean> appEnabled;
    List<String> appName;
    List<String> appPackage;
    String[] strApp;
    //String[] choosenApps;
    PackageManager pm;
    ListView appList;
    Button addb;
    boolean a;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        Set<String> appBlockedSet;
        pm = getPackageManager();
        appName = new LinkedList<>();
        appPackage = new LinkedList<>();
        setTitle("Выбор приложений");
        loadAppList a = new loadAppList();
        a.execute();
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        appList = findViewById(R.id.app_list);
        addb = findViewById(R.id.app_list_add_b);
        strApp = new String[appName.size()];
        for (int i = 0; i < appName.size(); i++)
        {
            strApp[i] = appName.get(i);
        }
        ArrayAdapter<String> lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strApp);
        appList.setAdapter(lista);
        SharedPreferences sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
        appBlockedSet = new HashSet<String>();
        if (sp.contains("blocked_apps"))
        {
            appBlockedSet = sp.getStringSet("blocked_apps", null);
        }
        if (appBlockedSet != null)
        {
            for (int i = 0; i < strApp.length; i++)
            {
                if (appBlockedSet.contains(appPackage.get(i)))
                {
                    appList.setItemChecked(i, true);
                }
            }
        }
        addb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SparseBooleanArray chApps = appList.getCheckedItemPositions();
                Set<String> chAppsSet = new HashSet<>();
                /*int k = 0;
                for (int i = 0; i < chApps.size(); i++)
                {
                    if (chApps.valueAt(i))
                    {
                        k++;
                    }
                }
                //choosenApps = new String[k];
                k = 0;*/
                for (int i = 0; i < appPackage.size(); i++)
                {
                    if (chApps.get(i))
                    {
                        //choosenApps[k] = strApp[i];
                        chAppsSet.add(appPackage.get(i));//strApp[i]);
                        //k++;
                    }
                }
                SharedPreferences sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sp.edit();
                spEditor.putStringSet("blocked_apps", chAppsSet);
                spEditor.apply();
                finish();
            }
        });
    }
    private class loadAppList extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            List<ApplicationInfo> appInfoList;
            appInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo as : appInfoList) {
                try
                {
                    PackageInfo pi = pm.getPackageInfo(as.packageName, 0);

                    if (ApplicationInfo.CATEGORY_GAME <= pi.applicationInfo.category
                            && pi.applicationInfo.category <= ApplicationInfo.CATEGORY_PRODUCTIVITY)
                    {
                        appPackage.add(as.packageName);
                        appName.add(pm.getApplicationLabel(as).toString());
                    }
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
