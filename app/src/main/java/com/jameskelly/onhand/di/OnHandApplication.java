package com.jameskelly.onhand.di;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;

public class OnHandApplication extends Application {

  private static AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();
    initAppComponents();
    Stetho.initializeWithDefaults(this);
  }

  public static OnHandApplication getInstance(Context context) {
    return (OnHandApplication) context.getApplicationContext();
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

  public void initAppComponents() {
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }
}
