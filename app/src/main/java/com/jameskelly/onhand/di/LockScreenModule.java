package com.jameskelly.onhand.di;

import com.jameskelly.onhand.lockscreen.LockScreenPresenter;
import com.jameskelly.onhand.lockscreen.LockScreenPresenterImpl;
import com.jameskelly.onhand.lockscreen.LockScreenView;
import dagger.Module;
import dagger.Provides;

@Module
public class LockScreenModule {

  private final LockScreenView view;

  public LockScreenModule(LockScreenView view) {
    this.view = view;
  }

  @Provides
  @ActivityScope
  LockScreenView provideLockScreenView() {
    return view;
  }

  @Provides
  @ActivityScope
  LockScreenPresenter provideLockScreenPresenter () {
    return new LockScreenPresenterImpl(view);
  }
}

