package com.jameskelly.onhand.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.jameskelly.onhand.lockscreen.LockScreenActivity;

public class OnHandServiceImpl extends Service implements OnHandService {

  @Override public void onCreate() {
    super.onCreate();

    IntentFilter filter = new IntentFilter();
    filter.addAction(Intent.ACTION_SCREEN_OFF);

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

    return START_STICKY;
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
    Toast.makeText(this, "create notification", Toast.LENGTH_SHORT).show();
  }

  @Override public void removeNotification() {
    Toast.makeText(this, "remove notification", Toast.LENGTH_SHORT).show();
  }
}
