package com.jameskelly.onhand.home;

import android.graphics.Bitmap;

public interface HomeView {

  void showPreviewImage(Bitmap previewBitmap);
  void showPreviewError();

  void showInitialButtons();

  void showCamera(); //intents work should be handled in the view?
  void showGallery();
}
