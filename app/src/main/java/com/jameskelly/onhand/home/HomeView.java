package com.jameskelly.onhand.home;

import android.content.Intent;
import android.graphics.Bitmap;

public interface HomeView {

  void showPreviewImage(Bitmap previewBitmap);
  void showPreviewError();

  void startCamera(Intent intent);
  void startGallery(Intent intent);
  void startOnHandService(Intent intent);
  void stopOnHandService(Intent intent);
}
