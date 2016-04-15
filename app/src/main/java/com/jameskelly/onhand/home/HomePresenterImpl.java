package com.jameskelly.onhand.home;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.jameskelly.onhand.service.OnHandService;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;

public class HomePresenterImpl implements HomePresenter {

  private HomeView homeView;

  @Inject ContentResolver contentResolver;

  public HomePresenterImpl(HomeView homeView) {
    this.homeView = homeView;
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
    ContentResolver cr = ((Activity)homeView).getContentResolver();
    cr.notifyChange(selectedImage, null);
    Bitmap bitmap = null;
    try {
      bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);

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
      homeView.showPreviewImage(bitmap);
    } else {
      homeView.showPreviewError();
    }
  }

  @Override public void confirmPreviewImage() {
    Intent intent = new Intent((Activity)homeView, OnHandService.class);
    homeView.startOnHandService(intent);
  }
}
