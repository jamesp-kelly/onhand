package com.jameskelly.onhand.base;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;
import java.io.IOException;

public class BasePresenterImpl implements BasePresenter {
  @Override public Bitmap getBitmapWithCorrectRotation(Context context, Uri imageUri) {
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
      Log.e(BasePresenterImpl.class.getSimpleName(), e.toString());
    }

    return bitmap;
  }
}
