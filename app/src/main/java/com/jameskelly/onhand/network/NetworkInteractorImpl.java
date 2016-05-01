package com.jameskelly.onhand.network;

import android.graphics.Bitmap;
import android.net.Uri;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class NetworkInteractorImpl implements NetworkInteractor {

  private Subscription subscription;

  @Override public void loadImageFromWeb(String imageUrl) {
    subscription = getBitmapObservable(Uri.parse("http://i.imgur.com/TA7I6Dg.jpg"))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(bitmapSubscriber);
  }

  Subscriber<Bitmap> bitmapSubscriber = new Subscriber<Bitmap>() {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onNext(Bitmap bitmap) {

    }
  };

  private Observable<Bitmap> getBitmapObservable(final Uri imageUri) {
    return Observable.defer(new Func0<Observable<Bitmap>>() {
      @Override public Observable<Bitmap> call() {
        return null;
      }
    });
  }
}
