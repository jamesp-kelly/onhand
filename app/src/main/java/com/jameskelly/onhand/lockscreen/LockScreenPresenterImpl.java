package com.jameskelly.onhand.lockscreen;

public class LockScreenPresenterImpl implements LockScreenPresenter {

  private LockScreenView lockScreenView;

  public LockScreenPresenterImpl(LockScreenView lockScreenView) {
    this.lockScreenView = lockScreenView;
  }
}
