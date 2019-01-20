package com.part.project.projectsettingspart;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity
{
    Button bDownload, bEdit, bSettings;

    public static boolean needPermissionForBlocking(Context context)
    {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return  (mode != AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        bDownload = findViewById(R.id.b_download);
        bEdit = findViewById(R.id.b_edit);
        bSettings = findViewById(R.id.b_settings);
        bDownload.setOnClickListener(click);
        bEdit.setOnClickListener(click);
        bSettings.setOnClickListener(click);
        setTitle("StudyBlock");
        if (needPermissionForBlocking(getApplicationContext()))
        {
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 0);
        }
        Log.d("mSTART", "                      start");
        startService(new Intent(MainActivity.this, MainService.class));
        /*SharedPreferences sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        Set<String> testSetNames = new HashSet<String>();
        testSetNames.add("firstSet");
        testSetNames.add("SetTest");
        spEditor.putStringSet("set_names", testSetNames);
        spEditor.apply();*/
    }

    private final View.OnClickListener click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.b_settings:
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    // go to settings
                    break;
                case R.id.b_edit:
                    startActivity(new Intent(MainActivity.this, SetActivity.class));
                    // go to card set list
                    break;
                case R.id.b_download:
                    // go to web part
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume()
    {
        super.onResume();
        App.getInstance().destroyActivityOnResume(this);
    }
}
