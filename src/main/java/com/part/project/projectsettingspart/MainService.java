package com.part.project.projectsettingspart;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import java.io.IOError;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TreeMap;

import static java.lang.System.in;


public class MainService extends IntentService
{
    final String LOG_TAG = "mLog";
    final String LOG_APP = "mAPP";
    Set<String> blockedApps;
    Map<String, Date> APPS;
    String lastDetectedApp = "";

    public MainService()
    {
        super("MainService");
    }

    private static MainService instance;

    public static MainService getInstance()
    {
        return instance;
    }

    public static Context getContext()
    {
        return instance;
        // or return instance.getApplicationContext();
    }

    void someTask()
    {
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<String> names = new ArrayList<>();
        int full = 0;
        while (true)
        {
            SharedPreferences sp;
            sp = (getApplicationContext()).getSharedPreferences("settings", Context.MODE_PRIVATE);
            if (sp.contains("blocked_apps"))
            {
                blockedApps = sp.getStringSet("blocked_apps", null);
            }
            else
            {
                blockedApps = null;
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            {
                UsageStatsManager usm = (UsageStatsManager)this.getSystemService(Context.USAGE_STATS_SERVICE);
                long time = System.currentTimeMillis();
                List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 10000*10000, time);
                /*if (appList != null && appList.size() == 0)
                {
                    Log.d(LOG_APP, "######### NO OPENED ACTIVITIES ##########");
                }*/
                if (appList != null && appList.size() > 0)
                {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStats : appList)
                    {
                        //Log.d(LOG_TAG, "usage stats executed : " +usageStats.getPackageName() + "\t\t ID: ");
                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty())
                    {
                        String currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        //Log.d(LOG_APP, currentApp);
                        if (lastDetectedApp != null && blockedApps != null && (!currentApp.equals(lastDetectedApp)) && blockedApps.contains(currentApp))
                        {
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (sp.getBoolean("block_option_0", false))
                            {
                                intent.setClass(this, FullBlockActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                if (sp.getBoolean("block_option_1", false))
                                {
                                    intent.setClass(this, CardViewActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    if (sp.getBoolean("block_option_2", false))
                                    {
                                        intent.setClass(this, ShowNoteActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                        if (!currentApp.equals("com.part.project.projectsettingspart"))
                        {
                            SharedPreferences.Editor spEditor = sp.edit();
                            spEditor.putInt("start_activity", 0);
                            spEditor.apply();
                            lastDetectedApp = currentApp;
                        }
                        //Log.d(LOG_APP, currentApp);
                    }
                }
            }

            try
            {
                Thread.sleep(500);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
    }


    public void createNotification()
    {
        //Log.d(LOG_TAG, "notify");
        int NOTIFICATION_ID = 1;
        String CHANNEL_NAME = "Notification Channel";
        if (android.os.Build.VERSION.SDK_INT < 26)
        {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);
            Notification notification = new NotificationCompat.Builder(MainService.this)
                    .setContentTitle("Manager of studying")
                    .setContentText("This notification can't be removed while program is working.")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    //.setTimeoutAfter(1)
                    .setAutoCancel(true)
                    .build();
            startForeground(NOTIFICATION_ID, notification);
            someTask();
        }
        else
        {
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            Notification notification = new Notification.Builder(MainService.this, CHANNEL_ID)
                    .setContentTitle("Manager of studying")
                    .setContentText("This notification can't be removed while program is working.")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setTimeoutAfter(1)
                    .setAutoCancel(true)
                    .build();
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            //mNotificationManager.notify(NOTIFICATION_ID, notification);
            startForeground(NOTIFICATION_ID, notification);
            //Log.d(LOG_TAG, "rrr");
            someTask();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d(LOG_TAG, "onHandleInput");
        createNotification();
        //return START_STICKY;
    }
}
