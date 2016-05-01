package com.jameskelly.onhand.home;

import android.graphics.Bitmap;
import com.jameskelly.onhand.model.ImageLoader;
import com.jameskelly.onhand.model.ScreenObject;
import com.jameskelly.onhand.repository.ScreenObjectRepository;

public class HomePresenterImpl implements HomePresenter, ImageLoader.ImageLoadListener {

  private static final int IMAGE_PREVIEW_DIMEN = 1920;

  private HomeView homeView;
  private ScreenObjectRepository screenObjectRepository;
  private ImageLoader imageLoader;

  public HomePresenterImpl(HomeView homeView, ScreenObjectRepository screenObjectRepository, ImageLoader imageLoader) {
    this.homeView = homeView;
    this.screenObjectRepository = screenObjectRepository;
    this.imageLoader = imageLoader;
  }

  @Override public void createScreenObject(String uriString) {
    ScreenObject screenObject = screenObjectRepository.createScreenObject(uriString, null);
    imageLoader.loadImage(screenObject.getImageUriString(), IMAGE_PREVIEW_DIMEN, this, true, true);
  }

  @Override public void loadActiveScreenObject() {

    ScreenObject activeScreenObject = screenObjectRepository.getActiveScreenObject();
    if (activeScreenObject != null) {
      imageLoader.loadImage(activeScreenObject.getImageUriString(), IMAGE_PREVIEW_DIMEN, this, true, false);
    }
  }

  @Override public void loadScreenObject(int screenObjectId) {
    ScreenObject screenObject = screenObjectRepository.getScreenObject(screenObjectId);
    if (screenObject != null) {
      imageLoader.loadImage(screenObject.getImageUriString(), IMAGE_PREVIEW_DIMEN, this, true, false);
    }
  }

  @Override public void onViewCreated() {
    screenObjectRepository.setupConnection();
  }

  @Override public void onViewDestroyed() {
    screenObjectRepository.closeConnection();
  }

  @Override public void onImageLoaded(Bitmap bitmap) {
    homeView.updatePreviewImage(bitmap);
  }

  @Override public void onImageLoadError() {
    homeView.showPreviewError();
  }
}
