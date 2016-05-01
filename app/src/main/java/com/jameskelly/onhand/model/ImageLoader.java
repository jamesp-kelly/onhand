package com.jameskelly.onhand.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ImageLoader {
  void loadImage(String url, int minDimen, ImageLoadListener listener, boolean debugFlags, boolean skipCacheLookup);
  void loadImageForImageView(String url, ImageView imageView, int minDimen, boolean debugFlags, boolean skipCacheLookup);

  interface ImageLoadListener {
    void onImageLoaded(Bitmap bitmap);
    void onImageLoadError();
  }
}
