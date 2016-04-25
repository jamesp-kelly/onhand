package com.jameskelly.onhand.di;

import android.content.SharedPreferences;
import com.jameskelly.onhand.home.HomePresenter;
import com.jameskelly.onhand.home.HomePresenterImpl;
import com.jameskelly.onhand.home.HomeView;
import com.jameskelly.onhand.repository.ScreenObjectRepository;
import com.jameskelly.onhand.repository.ScreenObjectRepositoryImpl;
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
  public ScreenObjectRepository provideScreenObjectRepository(SharedPreferences sharedPreferences) {
    return new ScreenObjectRepositoryImpl(sharedPreferences);
  }

  @Provides
  @ActivityScope
  HomePresenter provideHomePresenter(ScreenObjectRepository repository) {
    return new HomePresenterImpl(view, repository); //todo: fix the way presenter gets view
  }
}
