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
    homeView.showPreviewImage(screenObject.getUri(), false);
  }
}
