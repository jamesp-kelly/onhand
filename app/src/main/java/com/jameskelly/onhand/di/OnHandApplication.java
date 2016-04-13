package com.jameskelly.onhand.di;

import android.app.Application;
import android.content.Context;

public class OnHandApplication extends Application {

  private static AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();
    initAppComponents();
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
