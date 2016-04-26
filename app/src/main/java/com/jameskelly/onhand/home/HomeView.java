package com.jameskelly.onhand.home;

import com.jameskelly.onhand.base.BaseView;

public interface HomeView extends BaseView {

  void showPreviewImage(String imageUriString, boolean skipCacheLookup);
  void showPreviewError();

  void startCamera();
  void startGallery();

  void toggleOnHandService();
}
