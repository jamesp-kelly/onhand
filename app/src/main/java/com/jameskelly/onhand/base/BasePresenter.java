package com.jameskelly.onhand.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import java.io.IOException;

public interface BasePresenter {

  Bitmap correctBitmapRotation(Context context, Uri imageUri) throws IOException;
  Uri loadUriFromPreferences(String name);
}
