package com.jameskelly.onhand.repository;

import com.jameskelly.onhand.model.ScreenObject;
import java.util.List;

public interface ScreenObjectRepository {

  ScreenObject getScreenObject(int screenObjectId);
  ScreenObject getActiveScreenObject();
  List<ScreenObject> getAllPreviousScreenObjects();

  void activateScreenObject(long id);
  void updateImageUri(long id, String updatedUri);

  void detailScreenObjects();

  ScreenObject createScreenObject(String uriString, String message);
  void setupConnection();
  void closeConnection();
}
