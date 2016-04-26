package com.jameskelly.onhand.repository;

import com.jameskelly.onhand.model.ScreenObject;

public interface ScreenObjectRepository {
  ScreenObject getScreenObject(int screenObjectId);
  void saveScreenObject(ScreenObject screenObject);
  ScreenObject createScreenObject(String uriString, String message);
  void setupConnection();
  void closeConnection();
}
