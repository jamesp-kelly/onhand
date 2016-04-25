package com.jameskelly.onhand.repository;

import android.content.SharedPreferences;
import android.net.Uri;
import com.jameskelly.onhand.model.ScreenObject;

public class ScreenObjectRepositoryImpl implements ScreenObjectRepository {

  private SharedPreferences sharedPreferences;

  public ScreenObjectRepositoryImpl(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  @Override public ScreenObject getScreenObject(String key) {
    String sharedPreferencesString = sharedPreferences.getString(key, "");
    return new ScreenObject(Uri.parse(sharedPreferencesString));
  }

  @Override public void saveScreenObject(ScreenObject screenObject) {
  }
}
