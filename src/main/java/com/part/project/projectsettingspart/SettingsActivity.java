package com.part.project.projectsettingspart;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity
{
    ListView settingsList;
    String[] settingsNames = {"Настройки времени", "Cет для режима", "Блокируемые приложения"};
    List<String> appName;
    List<String> appPackage;
    PackageManager pm;
    boolean launchedActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        launchedActivity = false;
        pm = getPackageManager();
        setTitle("Настройки режима");
        settingsList = findViewById(R.id.settings_list);
        ArrayAdapter<String> lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingsNames);
        settingsList.setAdapter(lista);
        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id)
            {
                if (!launchedActivity)
                {
                    switch (p)
                    {
                        case 0:
                            //startActivity(new Intent(SettingsActivity.this, TimeActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(SettingsActivity.this, SetChooseActivity.class));
                            break;
                        case 2:
                            (new loadAppList()).execute();
                            break;
                        default:
                            break;
                    }
                    launchedActivity = true;
                }
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        launchedActivity = false;
    }

    private class loadAppList extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            List<ApplicationInfo> appInfoList;
            appInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            appName = new LinkedList<>();
            appPackage = new LinkedList<>();
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

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SettingsActivity.this, AppListActivity.class);
            String[] appNameArray = new String[appName.size()];
            String[] appPackageArray = new String[appPackage.size()];
            int i = 0;
            for (String s : appName)
            {
                appNameArray[i] = s;
                i++;
            }
            i = 0;
            for (String s : appPackage)
            {
                appPackageArray[i] = s;
                i++;
            }
            intent.putExtra("app_names", appNameArray);
            intent.putExtra("app_packages", appPackageArray);
            startActivity(intent);
        }
    }
}
