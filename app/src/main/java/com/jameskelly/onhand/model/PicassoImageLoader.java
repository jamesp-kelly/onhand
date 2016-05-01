package com.jameskelly.onhand.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.jameskelly.onhand.util.OnHandUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PicassoImageLoader implements ImageLoader {

  private final Picasso picasso;
  //private final Context context;
  private ImageLoadListener listener;

  public PicassoImageLoader(Picasso picasso) {
    this.picasso = picasso;
    //this.context = context;
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
    @Override public void onBitmapLoaded(android.graphics.Bitmap bitmap, Picasso.LoadedFrom from) {
      if (from.equals(Picasso.LoadedFrom.NETWORK)) {
        BitmapUri bitmapUri = saveOnlineImageLocally(bitmap);
        listener.onImageLoaded(bitmapUri.bitmap, bitmapUri.imageUri);
      }
      listener.onImageLoaded(bitmap, null);
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

  private BitmapUri saveOnlineImageLocally(Bitmap bitmap) {

    File imageFile = OnHandUtils.createNewImageFile();

    try {
      FileOutputStream outputStream = new FileOutputStream(imageFile);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new BitmapUri(bitmap, imageFile.toURI().toString());
  }

  private class BitmapUri {
    Bitmap bitmap;
    String imageUri;

    public BitmapUri(Bitmap bitmap, String imageUri) {
      this.bitmap = bitmap;
      this.imageUri = imageUri;
    }
  }
}
