package com.jameskelly.onhand.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class OnHandServiceImpl extends Service implements OnHandService {
  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }
}
