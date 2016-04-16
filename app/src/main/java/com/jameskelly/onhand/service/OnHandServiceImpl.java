package com.jameskelly.onhand.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.jameskelly.onhand.home.HomeActivity;
import com.jameskelly.onhand.lockscreen.LockScreenActivity;

public class OnHandServiceImpl extends Service implements OnHandService {

  private static int ONHAND_NOTIFICATION_ID = 1111;

  @Override public void onCreate() {
    super.onCreate();

    IntentFilter filter = new IntentFilter();
    filter.addAction(Intent.ACTION_SCREEN_OFF);
    registerReceiver(receiver, filter);
  }

  private final BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
        Intent i = new Intent(OnHandServiceImpl.this, LockScreenActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
      }
    }
  };

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i(OnHandServiceImpl.class.getSimpleName(), "starting service");
    createNotification();

    return START_NOT_STICKY;
  }

  @Override public void onDestroy() {
    Log.i(OnHandServiceImpl.class.getSimpleName(), "destroying service");
    removeNotification();
    unregisterReceiver(receiver);
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void createNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    builder.setAutoCancel(false)
        .setContentTitle("Content title")
        .setContentText("Content text")
        .setSmallIcon(android.R.drawable.stat_sys_warning)
        .setTicker("Ticker");

    Intent homeIntent = new Intent(this, HomeActivity.class);
    homeIntent.setAction(Intent.ACTION_MAIN);
    homeIntent.addCategory(Intent.CATEGORY_LAUNCHER);

    Intent stopIntent = new Intent(this, OnHandServiceImpl.class);
    stopIntent.setAction("STOP");

    builder.setContentIntent(PendingIntent.getActivity(this, 0, homeIntent, 0));
    //todo: stop intent not working
    builder.setDeleteIntent(PendingIntent.getService(this, 0, stopIntent, 0));

    NotificationManager notificationManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify(ONHAND_NOTIFICATION_ID, builder.build());
  }

  @Override public void removeNotification() {
    Toast.makeText(this, "remove notification", Toast.LENGTH_SHORT).show();
  }

  public static boolean isServiceRunning(Context context) {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (OnHandServiceImpl.class.getName().equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }
}
