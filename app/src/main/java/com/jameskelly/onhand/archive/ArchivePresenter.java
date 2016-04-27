package com.jameskelly.onhand.archive;

public interface ArchivePresenter {
  void loadScreenObjects();

  void onViewCreated(); //todo: created and destroyed repeated across presenters...
  void onViewDestroyed();
}
