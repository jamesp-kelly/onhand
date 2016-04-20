package com.jameskelly.onhand.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.base.BasePresenterImpl;
import com.jameskelly.onhand.service.OnHandServiceImpl;
import java.io.File;
import java.io.IOException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class HomePresenterImpl extends BasePresenterImpl implements HomePresenter {

  private HomeView homeView;
  private Context context;
  private SharedPreferences sharedPreferences;
  private Subscription subscription;

  public HomePresenterImpl(HomeView homeView) {
    super((Context) homeView);
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

  @Override public void loadPreviewImage(final Uri selectedImage) {
    subscription = getBitmapObservable(selectedImage)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Bitmap>() {
      @Override public void onCompleted() {
        updateSharedPrefs(context.getString(R.string.shared_prefs_saved_image),
            selectedImage.toString());
      }

      @Override public void onError(Throwable e) {
        Log.e(HomePresenterImpl.class.getSimpleName(), e.getMessage(), e);
        homeView.showPreviewError();
      }

      @Override public void onNext(Bitmap bitmap) {
        //homeView.showPreviewImage(bitmap);
      }
    });
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

  private Observable<Bitmap> getBitmapObservable(final Uri imageUri) {
    return Observable.defer(new Func0<Observable<Bitmap>>() {
      @Override public Observable<Bitmap> call() {
        try {
          return Observable.just(correctBitmapRotation(context, imageUri));
        } catch (IOException e) {
          return Observable.error(e);
        }
      }
    });
  }

  @Override public void onDestroy() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }

  @Override public Uri loadUriFromPreferences(String name) {
    return super.loadUriFromPreferences(name);
  }
}
