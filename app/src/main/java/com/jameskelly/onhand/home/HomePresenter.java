package com.jameskelly.onhand.home;

import android.net.Uri;

public interface HomePresenter {
  void openCamera();
  void openGallery();

  void loadPreviewImage(Uri selectedImage);
  void toggleService(boolean start);
  boolean updateSharedPrefs(String key, String uriString);
}
