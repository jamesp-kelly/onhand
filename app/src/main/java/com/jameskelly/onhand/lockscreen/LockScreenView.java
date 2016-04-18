package com.jameskelly.onhand.lockscreen;

import android.graphics.Bitmap;
import com.jameskelly.onhand.base.BaseView;

public interface LockScreenView extends BaseView {
  void setupWindow();
  void hideWindow();
  void showImage(Bitmap savedImage);
}
