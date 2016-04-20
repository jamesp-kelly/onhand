package com.jameskelly.onhand.lockscreen;

import android.net.Uri;

public interface LockScreenPresenter {
  void loadSavedImageFromPrefs();
  void onDestroy();
  Uri loadUriFromPreferences(String name);
}
