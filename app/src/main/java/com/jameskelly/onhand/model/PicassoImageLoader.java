package com.jameskelly.onhand.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

public class PicassoImageLoader implements ImageLoader {

  private final Picasso picasso;
  private ImageLoadListener listener;

  public PicassoImageLoader(Picasso picasso) {
    this.picasso = picasso;
  }

  @Override public void loadImage(String url, int minDimen, ImageLoadListener listener,
      boolean debugFlags, boolean skipCacheLookup) {

    this.listener = listener;
    picasso.setIndicatorsEnabled(debugFlags);
    RequestCreator requestCreator = picasso.load(url).resize(minDimen, minDimen).centerInside();
    if (skipCacheLookup) {
      requestCreator =
          requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE);
    }
    requestCreator.into(target);
  }

  final Target target = new Target() {
    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      if (from.equals(Picasso.LoadedFrom.NETWORK)) {
        //store to disk and return the local bitmap?
      }
      listener.onImageLoaded(bitmap);
    }
    @Override public void onBitmapFailed(Drawable errorDrawable) {
      listener.onImageLoadError();
    }
    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {}
  };

  @Override public void loadImageForImageView(String url, ImageView imageView, int minDimen, boolean debugFlags,
      boolean skipCacheLookup) {
    picasso.setIndicatorsEnabled(debugFlags);
    RequestCreator requestCreator = picasso.load(url).resize(minDimen, minDimen).centerInside();
    if (skipCacheLookup) {
      requestCreator =
          requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE);
    }
    requestCreator.into(imageView);
  }
}
