package com.jameskelly.onhand.repository;

import com.jameskelly.onhand.model.ScreenObject;

public interface ScreenObjectRepository {
  ScreenObject getScreenObject(String key);
  void saveScreenObject(ScreenObject screenObject);
}
