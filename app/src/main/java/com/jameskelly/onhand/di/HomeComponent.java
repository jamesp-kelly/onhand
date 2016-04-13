package com.jameskelly.onhand.di;

import com.jameskelly.onhand.home.HomeActivity;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {HomeModule.class})
public interface HomeComponent {
  void inject(HomeActivity homeActivity);
}
