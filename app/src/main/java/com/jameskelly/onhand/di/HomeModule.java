package com.jameskelly.onhand.di;

import com.jameskelly.onhand.home.HomePresenter;
import com.jameskelly.onhand.home.HomePresenterImpl;
import com.jameskelly.onhand.home.HomeView;
import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

  public final HomeView view;

  public HomeModule(HomeView view) {
    this.view = view;
  }

  @Provides
  @ActivityScope
  HomeView provideHomeView() {
    return view;
  }

  @Provides
  @ActivityScope
  HomePresenter provideHomePresenter() {
    return new HomePresenterImpl(view);
  }
}
