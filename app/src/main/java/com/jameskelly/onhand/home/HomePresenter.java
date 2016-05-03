package com.jameskelly.onhand.home;

public interface HomePresenter {
  void loadScreenObject(int screenObjectId);
  void createScreenObject(String uriString);
  void onViewCreated();
  void onViewDestroyed();
  void loadActiveScreenObject();

  void loadArchiveScreenObjects();
}
