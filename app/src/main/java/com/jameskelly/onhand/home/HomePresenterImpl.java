package com.jameskelly.onhand.home;

import com.jameskelly.onhand.model.ScreenObject;
import com.jameskelly.onhand.repository.ScreenObjectRepository;

public class HomePresenterImpl implements HomePresenter {

  private HomeView homeView;
  private ScreenObjectRepository screenObjectRepository;

  public HomePresenterImpl(HomeView homeView, ScreenObjectRepository screenObjectRepository) {
    this.homeView = homeView;
    this.screenObjectRepository = screenObjectRepository;
  }

  @Override public void loadPreviewImage(int screenObjectId) {
    ScreenObject screenObject = screenObjectRepository.getScreenObject(screenObjectId);
    if (screenObject != null) {
      homeView.showPreviewImage(screenObject.getImageUriString(), false);
    }
  }

  @Override public void createScreenObject(String uriString) {
    ScreenObject screenObject = screenObjectRepository.createScreenObject(uriString, null);
    homeView.showPreviewImage(screenObject.getImageUriString(), true);
  }

  @Override public void onViewCreated() {
    screenObjectRepository.setupConnection();
  }

  @Override public void onViewDestroyed() {
    screenObjectRepository.closeConnection();
  }
}
