package com.jameskelly.onhand.di;

import com.jameskelly.onhand.service.OnHandService;
import com.jameskelly.onhand.service.OnHandServiceImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ServiceModule {

  @Provides
  @Singleton
  OnHandService provideOnHandService() {
    return new OnHandServiceImpl();
  }

}
