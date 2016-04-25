package com.jameskelly.onhand.home;

import android.net.Uri;

public interface HomePresenter {

  //void loadPreviewImageFromPrefs();
  void loadPreviewImage(String key);
  void toggleService(boolean start, Uri imageUri);
  //boolean updateSharedPrefs(String key, String uriString);
  //Uri loadUriFromPreferences(String name);
}
