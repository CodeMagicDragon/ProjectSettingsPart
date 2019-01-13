package com.part.project.projectsettingspart;

import android.app.Activity;
import android.app.ActivityManager;
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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOError;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class MainService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.up.service.action.FOO";
    private static final String ACTION_BAZ = "com.up.service.action.BAZ";
    final String LOG_TAG = "myLog";
    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.up.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.up.service.extra.PARAM2";
    private NotificationManager mNotificationManager;

    public MainService() {
        super("MainService");
    }

    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MainService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MainService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    void someTask() {
        while (true) {
            final PackageManager pm = getPackageManager();
            List<ApplicationInfo> apps = new ArrayList<>();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            List<String> names = new ArrayList<>();
            for (int i = 0; i < packages.size(); i++){
                names.add(pm.getApplicationLabel(packages.get(i)).toString());
            }

            for (int i = 0; i < packages.size(); i++){
                //return p.getApplicationLabel(packageInfo).toString();
                //ApplicationInfo info = packages.get(i);
                Log.d(LOG_TAG,  names.get(i) + ", " + packages.get(i).enabled);
            }
            Date currentTime = Calendar.getInstance().getTime();
            Log.d(LOG_TAG, currentTime.toString());
            try
            {
                Thread.sleep(3000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
    }


    public void createNotification(){
        Log.d(LOG_TAG, "notify");
        String NOTIFICATION_CHANNEL_ID = "channel_id";
        int NOTIFICATION_ID = 1;
        String CHANNEL_NAME = "Notification Channel";
        if (android.os.Build.VERSION.SDK_INT < 26) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_NAME)
                    .setContentTitle("Manager of studying")
                    .setContentText("This notification can't be removed while program is working.")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .setTimeoutAfter(1)
                    .setAutoCancel(true)
                    .build();
            startForeground(NOTIFICATION_ID, notification);
            someTask();
        }
        else{
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_LOW;
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
            Log.d(LOG_TAG, "rrr");
            someTask();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "onHandleInput");
        createNotification();
        //return START_STICKY;
    }



    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
