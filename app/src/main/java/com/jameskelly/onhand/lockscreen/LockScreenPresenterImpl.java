package com.jameskelly.onhand.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.base.BasePresenterImpl;
import java.io.IOException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class LockScreenPresenterImpl extends BasePresenterImpl implements LockScreenPresenter {

  private LockScreenView lockScreenView;
  private Context context;
  private SharedPreferences sharedPreferences;
  private Subscription subscription;

  public LockScreenPresenterImpl(final LockScreenView lockScreenView) {
    this.lockScreenView = lockScreenView;
    this.context = (Context) lockScreenView;
    this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Override public void loadSavedImageFromPrefs() {
    String savedImageUri = sharedPreferences
        .getString(context.getString(R.string.shared_prefs_saved_image), "");
    if (!savedImageUri.isEmpty()) {
      subscription = getBitmapObservable(Uri.parse(savedImageUri))
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Subscriber<Bitmap>() {
        @Override public void onCompleted() {
        }

        @Override public void onError(Throwable e) {
          Log.e(LockScreenPresenterImpl.class.getSimpleName(), e.getMessage(), e);
        }

        @Override public void onNext(Bitmap bitmap) {
          lockScreenView.showImage(bitmap);
        }
      });
    }
  }

  public Observable<Bitmap> getBitmapObservable(final Uri imageUri) {
    return Observable.defer(new Func0<Observable<Bitmap>>() {
      @Override public Observable<Bitmap> call() {
        try {
          return Observable.just(correctBitmapRotation(context, imageUri));
        } catch (IOException e) {
          return null;
        }
      }
    });
  }

  @Override public void onDestroy() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
