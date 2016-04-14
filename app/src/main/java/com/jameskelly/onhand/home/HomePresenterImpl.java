package com.jameskelly.onhand.home;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;
import com.jameskelly.onhand.util.NotImplementedException;
import java.io.IOException;
import javax.inject.Inject;

public class HomePresenterImpl implements HomePresenter {

  private HomeView homeView;

  @Inject ContentResolver contentResolver;

  public HomePresenterImpl(HomeView homeView) {
    this.homeView = homeView;
  }

  @Override public void openCamera() {
    homeView.showCamera();
  }

  @Override public void openGallery() {
    homeView.showGallery();
  }

  @Override public void loadPreviewImage(Uri selectedImage, ContentResolver contentResolver) {
    contentResolver.notifyChange(selectedImage, null);
    Bitmap bitmap = null;
    try {
      bitmap = android.provider.MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);

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
    throw new NotImplementedException();
  }
}
