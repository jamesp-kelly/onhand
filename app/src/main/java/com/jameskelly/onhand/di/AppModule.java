package com.jameskelly.onhand.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jameskelly.onhand.model.ImageLoader;
import com.jameskelly.onhand.model.PicassoImageLoader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module
public class AppModule {
  OnHandApplication application;

  public AppModule(OnHandApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  protected Application provideApplication() {
    return application;
  }

  @Provides
  @Singleton
  protected Resources provideResources() {
    return application.getResources();
  }

  @Provides
  @Singleton
  protected SharedPreferences provideSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }

  @Provides
  @Singleton
  protected OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
        .addNetworkInterceptor(new StethoInterceptor())
        .build();
  }

  @Provides
  @Singleton
  public Picasso providePicasso(Application onHandApp, OkHttpClient okHttpClient) {
    return new Picasso.Builder(onHandApp)
        .downloader(new OkHttp3Downloader(okHttpClient))
        .build();
  }

  @Provides
  @Singleton
  public ImageLoader provideImageLoader(@NonNull Picasso picasso) {
    return new PicassoImageLoader(picasso);
  }
}
