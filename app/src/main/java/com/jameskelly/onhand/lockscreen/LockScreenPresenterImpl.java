package com.jameskelly.onhand.lockscreen;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.home.HomeActivity;
import java.io.IOException;

public class LockScreenPresenterImpl implements LockScreenPresenter {

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
      ContentResolver cr = context.getContentResolver();
      cr.notifyChange(imageUri, null);
      Bitmap bitmap = null;
      try {
        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, imageUri);

        //force portrait todo: more general approach for handling image rotation
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
      } catch (IOException e) {
        Log.e(HomeActivity.class.getSimpleName(), e.toString());
      }

      if (bitmap != null) {
        lockScreenView.showImage(bitmap);
      } else {
        //show error on the lock screen??
      }
    }
  }
}
