package com.jameskelly.onhand.home;

import android.content.Intent;
import android.net.Uri;
import com.jameskelly.onhand.base.BaseView;

public interface HomeView extends BaseView {

  void showPreviewImage(Uri imageUri, boolean skipCacheLookup);
  void showPreviewError();

  void startCamera(Intent intent);
  void startGallery(Intent intent);
  void startOnHandService(Intent intent);
  void stopOnHandService(Intent intent);
}
