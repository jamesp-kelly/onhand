package com.jameskelly.onhand.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.base.BasePresenterImpl;

public class LockScreenPresenterImpl extends BasePresenterImpl implements LockScreenPresenter {

  private LockScreenView lockScreenView;
  private Context context;
  private SharedPreferences sharedPreferences;

  public LockScreenPresenterImpl(LockScreenView lockScreenView) {
    this.lockScreenView = lockScreenView;
    this.context = (Context) lockScreenView;
    this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  //todo - lots of repeated code here and homescreenpresenterimpl
  @Override public void loadSavedImageFromPrefs() {
    String savedImageUri = sharedPreferences
        .getString(context.getString(R.string.shared_prefs_saved_image), "");
    if (!savedImageUri.isEmpty()) {
      Uri imageUri = Uri.parse(savedImageUri);
      Bitmap bitmap = getBitmapWithCorrectRotation(context, imageUri);

      if (bitmap != null) {
        lockScreenView.showImage(bitmap);
      } else {
        //show error on the lock screen??
      }
    }
  }
}
