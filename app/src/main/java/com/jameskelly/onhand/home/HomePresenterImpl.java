package com.jameskelly.onhand.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.base.BasePresenterImpl;
import com.jameskelly.onhand.service.OnHandServiceImpl;
import java.io.File;

public class HomePresenterImpl extends BasePresenterImpl implements HomePresenter {

  private HomeView homeView;
  private Context context;
  private SharedPreferences sharedPreferences;

  public HomePresenterImpl(HomeView homeView) {
    this.homeView = homeView;
    this.context = (Context) homeView;
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Override public void openCamera() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    File photo = new File(Environment.getExternalStorageDirectory(), "ON_HAND_CAPTURE.jpg");
    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    homeView.startCamera(intent);
  }

  @Override public void openGallery() {
    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    homeView.startGallery(intent);
  }

  @Override public void loadPreviewImage(Uri selectedImage) {

    Bitmap bitmap = getBitmapWithCorrectRotation(context, selectedImage);

    if (bitmap != null) {
      homeView.showPreviewImage(bitmap);
      updateSharedPrefs(context.getString(R.string.shared_prefs_saved_image),
          selectedImage.toString());
    } else {
      homeView.showPreviewError();
    }
  }

  @Override public void loadPreviewImageFromPrefs() {
    String savedImageUri = sharedPreferences
        .getString(context.getString(R.string.shared_prefs_saved_image), "");
    if (!savedImageUri.isEmpty()) {
      loadPreviewImage(Uri.parse(savedImageUri));
    }
  }

  @Override public void toggleService(boolean start) {
    Intent intent = new Intent(context, OnHandServiceImpl.class);
    if (start) {
      homeView.startOnHandService(intent);
    } else {
      homeView.stopOnHandService(intent);
      updateSharedPrefs(context.getString(R.string.shared_prefs_saved_image), "");
    }
  }

  @Override public boolean updateSharedPrefs(String key, String uriString) {
    return !uriString.isEmpty() ? sharedPreferences.edit().putString(key, uriString).commit() :
        sharedPreferences.edit().remove(key).commit();
  }
}
