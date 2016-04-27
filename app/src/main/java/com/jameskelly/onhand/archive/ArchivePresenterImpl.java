package com.jameskelly.onhand.archive;

import com.jameskelly.onhand.model.ScreenObject;
import com.jameskelly.onhand.repository.ScreenObjectRepository;
import java.util.List;

public class ArchivePresenterImpl implements ArchivePresenter {

  private final ArchiveView archiveView;
  private final ScreenObjectRepository screenObjectRepository;

  public ArchivePresenterImpl(ArchiveView archiveView, ScreenObjectRepository screenObjectRepository) {
    this.archiveView = archiveView;
    this.screenObjectRepository = screenObjectRepository;
  }

  @Override public void loadScreenObjects() {
    List<ScreenObject> screenObjects =
        screenObjectRepository.getAllPreviousScreenObjects();

    archiveView.displayScreenObjects(screenObjects);
  }

  @Override public void onViewCreated() {
    screenObjectRepository.setupConnection();
  }

  @Override public void onViewDestroyed() {
    screenObjectRepository.closeConnection();
  }
}
