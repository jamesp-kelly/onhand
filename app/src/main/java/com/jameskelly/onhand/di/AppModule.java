package com.jameskelly.onhand.di;

import android.app.Application;
import android.content.res.Resources;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

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
}
