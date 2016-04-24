package com.jameskelly.onhand.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.home.HomeActivity;
import com.jameskelly.onhand.lockscreen.LockScreenActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static android.support.v4.app.NotificationCompat.PRIORITY_HIGH;

public class OnHandServiceImpl extends Service implements OnHandService {

  private static final int ONHAND_NOTIFICATION_ID = 1111;
  private static final String STOP_SERVICE = "com.jameskelly.onhand.StopService";

  private NotificationManager notificationManager;

  @Override public void onCreate() {
    super.onCreate();

    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    IntentFilter filter = new IntentFilter();
    filter.addAction(Intent.ACTION_SCREEN_OFF);
    filter.addAction(STOP_SERVICE);

    registerReceiver(receiver, filter);
  }

  private final BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      Log.i(OnHandServiceImpl.class.getSimpleName(), "in onReceive()");
      if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
        Intent i = new Intent(OnHandServiceImpl.this, LockScreenActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
      } else if (intent.getAction().equals(STOP_SERVICE)) {
        OnHandServiceImpl.this.stopSelf();
      }
    }
  };

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i(OnHandServiceImpl.class.getSimpleName(), "starting service");
    Uri onHandImageUri = intent.getParcelableExtra("onHandImageUri");

    //load image, start notification in image loaded callback
    Picasso.with(this).load(onHandImageUri).into(notificationImageTarget);

    return START_NOT_STICKY;
  }

  @Override public void onDestroy() {
    Log.i(OnHandServiceImpl.class.getSimpleName(), "destroying service");
    unregisterReceiver(receiver);

    notificationManager.cancel(ONHAND_NOTIFICATION_ID);

    //kill lockscreen activity
    Intent finishLockScreenIntent = new Intent(getString(R.string.finish_lockscreen_activity));
    sendBroadcast(finishLockScreenIntent);
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void createNotification(Bitmap notificationBitmap) {
    Intent homeIntent = new Intent(this, HomeActivity.class);
    homeIntent.setAction(Intent.ACTION_MAIN);
    homeIntent.addCategory(Intent.CATEGORY_LAUNCHER);

    Intent stopIntent = new Intent(STOP_SERVICE);

    PendingIntent homePendingIntent = PendingIntent.getActivity(this, 0, homeIntent, 0);
    PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 0, stopIntent, 0);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    builder.setAutoCancel(false)
        .setPriority(PRIORITY_HIGH)
        .setContentTitle(getString(R.string.notification_content_title))
        .setContentText(getString(R.string.notification_content_text))
        .setSmallIcon(android.R.drawable.stat_sys_warning)
        .setTicker(getString(R.string.notification_ticker))
        .addAction(R.drawable.ic_info_black_24dp, getString(R.string.notification_cancel), stopPendingIntent)
        .setContentIntent(homePendingIntent)
        .setDeleteIntent(stopPendingIntent)
        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(notificationBitmap));


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      builder.setCategory(Notification.CATEGORY_SERVICE);
    }

    notificationManager.notify(ONHAND_NOTIFICATION_ID, builder.build());
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

  private Target notificationImageTarget = new Target() {
    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      createNotification(bitmap);
    }

    @Override public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
  };
}
