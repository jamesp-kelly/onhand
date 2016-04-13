package com.jameskelly.onhand.di;

import com.jameskelly.onhand.lockscreen.LockScreenActivity;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {LockScreenModule.class})
public interface LockScreenComponent {
  void inject(LockScreenActivity lockScreenActivity);
}
