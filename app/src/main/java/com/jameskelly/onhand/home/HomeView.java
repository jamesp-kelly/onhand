package com.jameskelly.onhand.home;

import android.graphics.Bitmap;
import com.jameskelly.onhand.base.BaseView;
import com.jameskelly.onhand.model.ScreenObject;
import java.util.List;

public interface HomeView extends BaseView {

  //void showPreviewImage(String imageUriString, boolean skipCacheLookup);
  void updatePreviewImage(Bitmap bitmap);
  void showPreviewError();

  void startCamera();
  void startGallery();

  void navigateToArchive();

  void toggleOnHandService();

  void displayArchiveScreenObjects(List<ScreenObject> screenObjects);
}
