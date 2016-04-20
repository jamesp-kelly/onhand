package com.jameskelly.onhand.base;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import java.io.IOException;

public class BasePresenterImpl implements BasePresenter {
  @Override public Bitmap correctBitmapRotation(Context context, Uri imageUri) throws IOException {
    ContentResolver cr = context.getContentResolver();
    cr.notifyChange(imageUri, null);
    Bitmap bitmap = null;

    bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, imageUri);

    ExifInterface exif = null;
    try {
      exif = new ExifInterface(imageUri.getPath());
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (exif != null) {
      int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
      bitmap = rotateBitmap(bitmap, orientation);
    }

    return bitmap;
  }

  private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
    Matrix matrix = new Matrix();
    switch (orientation) {
      case ExifInterface.ORIENTATION_NORMAL:
        return bitmap;
      case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
        matrix.setScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_ROTATE_180:
        matrix.setRotate(180);
        break;
      case ExifInterface.ORIENTATION_FLIP_VERTICAL:
        matrix.setRotate(180);
        matrix.postScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_TRANSPOSE:
        matrix.setRotate(90);
        matrix.postScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_ROTATE_90:
        matrix.setRotate(90);
        break;
      case ExifInterface.ORIENTATION_TRANSVERSE:
        matrix.setRotate(-90);
        matrix.postScale(-1, 1);
        break;
      case ExifInterface.ORIENTATION_ROTATE_270:
        matrix.setRotate(-90);
        break;
      default:
        return bitmap;
    }
    try {
      Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
          bitmap.getWidth(), bitmap.getHeight(), matrix, true);
      bitmap.recycle();
      return rotatedBitmap;
    } catch (OutOfMemoryError e) {
      e.printStackTrace();
      return null;
    }
  }
}
