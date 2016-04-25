package com.jameskelly.onhand.home;

import android.net.Uri;
import com.jameskelly.onhand.model.ScreenObject;
import com.jameskelly.onhand.repository.ScreenObjectRepository;

public class HomePresenterImpl implements HomePresenter {

  private HomeView homeView;
  private ScreenObjectRepository screenObjectRepository;


  public HomePresenterImpl(HomeView homeView, ScreenObjectRepository screenObjectRepository) {
    this.homeView = homeView;
    this.screenObjectRepository = screenObjectRepository;
  }

  @Override public void loadPreviewImage(String key) {
    ScreenObject screenObject = screenObjectRepository.getScreenObject(key);
    homeView.showPreviewImage(screenObject.getUri(), false);
  }

  @Override public void toggleService(boolean start, Uri imageUri) {
    //Intent intent = new Intent(context, OnHandServiceImpl.class);
    //intent.putExtra("onHandImageUri", imageUri);
    //if (start) {
    //  homeView.startOnHandService(intent);
    //} else {
    //  homeView.stopOnHandService(intent);
      //updateSharedPrefs(context.getString(R.string.shared_prefs_saved_image), ""); //todo
    //}
  }

  //@Override public boolean updateSharedPrefs(String key, String uriString) {
  //  return !uriString.isEmpty() ? sharedPreferences.edit().putString(key, uriString).commit() :
  //      sharedPreferences.edit().remove(key).commit();
  //}

}
