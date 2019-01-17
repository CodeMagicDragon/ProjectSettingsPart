package com.part.project.projectsettingspart;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public class LoadActivity extends AppCompatActivity
{
    List<String> appName;
    List<String> appPackage;
    PackageManager pm;
    boolean launchedOnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        pm = getPackageManager();
        (new loadAppList()).execute();
        launchedOnCreate = true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (launchedOnCreate)
        {
            launchedOnCreate = !launchedOnCreate;
        }
        else
        {
            finish();;
        }
    }

    private class loadAppList extends AsyncTask<Void, Void, Void>
    {
        protected Void doInBackground(Void... voids)
        {
            List<ApplicationInfo> appInfoList;
            appInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            appName = new LinkedList<>();
            appPackage = new LinkedList<>();
            for (ApplicationInfo as : appInfoList) {
                /*try
                {*/
                //PackageInfo pi = pm.getPackageInfo(as.packageName, 0);
                if ((/*ApplicationInfo.CATEGORY_GAME*/ 0 <= as.category
                        && as.category <= 7 /*ApplicationInfo.CATEGORY_PRODUCTIVITY*/) || as.packageName.equals("com.vkontakte.android"))
                {
                    appPackage.add(as.packageName);
                    appName.add(pm.getApplicationLabel(as).toString());
                }
                /*}
                catch (PackageManager.NameNotFoundException e)
                {
                    e.printStackTrace();
                }*/
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(LoadActivity.this, AppListActivity.class);
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
