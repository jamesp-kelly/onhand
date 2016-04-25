package com.jameskelly.onhand.home;

import android.net.Uri;
import com.jameskelly.onhand.base.BaseView;

public interface HomeView extends BaseView {

  void showPreviewImage(Uri imageUri, boolean skipCacheLookup);
  void showPreviewError();

  void startCamera();
  void startGallery();

  void toggleOnHandService();
}
