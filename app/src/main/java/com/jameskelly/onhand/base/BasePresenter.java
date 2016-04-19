package com.jameskelly.onhand.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

public interface BasePresenter {

  Bitmap getBitmapWithCorrectRotation(Context context, Uri imageUri);
}
