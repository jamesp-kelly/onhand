package com.jameskelly.onhand.home;

import android.graphics.Bitmap;
import com.jameskelly.onhand.model.ImageLoader;
import com.jameskelly.onhand.model.ScreenObject;
import com.jameskelly.onhand.repository.ScreenObjectRepository;
import java.util.List;

public class HomePresenterImpl implements HomePresenter, ImageLoader.ImageLoadListener {

  private static final int IMAGE_PREVIEW_DIMEN = 1920;

  private HomeView homeView;
  private ScreenObjectRepository screenObjectRepository;
  private ImageLoader imageLoader;
  private ScreenObject currentScreenObject;

  public HomePresenterImpl(HomeView homeView, ScreenObjectRepository screenObjectRepository, ImageLoader imageLoader) {
    this.homeView = homeView;
    this.screenObjectRepository = screenObjectRepository;
    this.imageLoader = imageLoader;
  }

  @Override public void createScreenObject(String uriString) {
    currentScreenObject = screenObjectRepository.createScreenObject(uriString, null);
    imageLoader.loadImage(currentScreenObject.getImageUriString(), IMAGE_PREVIEW_DIMEN, this, true, true);
  }

  @Override public void loadActiveScreenObject() {
    currentScreenObject = screenObjectRepository.getActiveScreenObject();

    if (currentScreenObject != null) {
      imageLoader.loadImage(currentScreenObject.getImageUriString(), IMAGE_PREVIEW_DIMEN, this, true, false);
    }
  }

  @Override public void loadScreenObject(int screenObjectId) {
    currentScreenObject = screenObjectRepository.getScreenObject(screenObjectId);
    if (currentScreenObject != null) {
      imageLoader.loadImage(currentScreenObject.getImageUriString(), IMAGE_PREVIEW_DIMEN, this, true, false);
    }
  }

  @Override public void onViewCreated() {
    screenObjectRepository.setupConnection();
  }

  @Override public void onViewDestroyed() {
    screenObjectRepository.closeConnection();
  }

  @Override public void onImageLoaded(Bitmap bitmap, String updatedUri) {
    homeView.updatePreviewImage(bitmap);
    if (updatedUri != null) {
      screenObjectRepository.updateImageUri(currentScreenObject.getId(), updatedUri);
    }
  }

  @Override public void onImageLoadError() {
    homeView.showPreviewError();
  }

  @Override public void loadArchiveScreenObjects() {
    final List<ScreenObject> previousScreenObjects =
        screenObjectRepository.getAllPreviousScreenObjects();

    homeView.displayArchiveScreenObjects(previousScreenObjects);
  }
}
