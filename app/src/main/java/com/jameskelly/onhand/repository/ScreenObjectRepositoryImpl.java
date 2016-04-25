package com.jameskelly.onhand.repository;

import android.content.SharedPreferences;
import android.net.Uri;
import com.jameskelly.onhand.model.ScreenObject;

public class ScreenObjectRepositoryImpl implements ScreenObjectRepository {

  private SharedPreferences sharedPreferences;

  //temporary. Just hold a single screenObject for now
  private ScreenObject screenObject;

  public ScreenObjectRepositoryImpl(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;

    //temp
    String sharedPreferencesString = sharedPreferences.getString("saved_image", "");
    this.screenObject = new ScreenObject(Uri.parse(sharedPreferencesString));

  }

  @Override public ScreenObject getScreenObject(int screenObjectId) {

    //ignore screenObjectId until using multiple screenObjects
    return screenObject;
  }

  @Override public void saveScreenObject(ScreenObject screenObject) {
    //unused for now
  }
}
