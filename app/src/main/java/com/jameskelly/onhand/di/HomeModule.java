package com.jameskelly.onhand.di;

import android.app.Application;
import android.content.SharedPreferences;
import com.jameskelly.onhand.home.HomePresenter;
import com.jameskelly.onhand.home.HomePresenterImpl;
import com.jameskelly.onhand.home.HomeView;
import com.jameskelly.onhand.repository.ScreenObjectRepository;
import com.jameskelly.onhand.repository.RealmScreenObjectRepository;
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
  public ScreenObjectRepository provideScreenObjectRepository(Application application) {
    return new RealmScreenObjectRepository(application.getApplicationContext());
  }

  @Provides
  @ActivityScope
  HomePresenter provideHomePresenter(ScreenObjectRepository repository) {
    return new HomePresenterImpl(view, repository); //todo: fix the way presenter gets view
  }
}
