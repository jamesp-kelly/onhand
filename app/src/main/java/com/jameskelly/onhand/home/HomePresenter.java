package com.jameskelly.onhand.home;

import android.content.ContentResolver;
import android.net.Uri;

public interface HomePresenter {
  void openCamera();
  void openGallery();

  void loadPreviewImage(Uri selectedImage, ContentResolver contentResolver);
  void confirmPreviewImage();
}
