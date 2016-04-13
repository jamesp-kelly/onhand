package com.jameskelly.onhand.home;

import com.jameskelly.onhand.util.NotImplementedException;

public class HomePresenterImpl implements HomePresenter {

  private HomeView homeView;

  public HomePresenterImpl(HomeView homeView) {
    this.homeView = homeView;
  }

  @Override public void openCamera() {
    homeView.showCamera();
  }

  @Override public void openGallery() {
    homeView.showGallery();
  }

  @Override public void loadPreviewImage() {
    throw new NotImplementedException();
  }

  @Override public void confirmPreviewImage() {
    throw new NotImplementedException();
  }
}
