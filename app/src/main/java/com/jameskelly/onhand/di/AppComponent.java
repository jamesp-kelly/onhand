package com.jameskelly.onhand.di;

import android.app.Application;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, ServiceModule.class})
public interface AppComponent {

  Application application();
  HomeComponent plus(HomeModule homeModule);
  LockScreenComponent plus(LockScreenModule lockScreenModule);
}
