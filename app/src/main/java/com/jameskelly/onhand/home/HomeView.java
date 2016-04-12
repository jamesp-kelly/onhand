package com.jameskelly.onhand.home;

public interface HomeView {

  void showPreviewImage(boolean confirmed);
  void showPreviewError();

  void showInitialButtons();

  void showCamera(); //intents work should be handled in the view?
  void showGallery();
}
