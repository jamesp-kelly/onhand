package com.jameskelly.onhand.archive;

import com.jameskelly.onhand.base.BaseView;
import com.jameskelly.onhand.model.ScreenObject;
import java.util.List;

public interface ArchiveView extends BaseView {
  void displayScreenObjects(List<ScreenObject> screenObjects);
}
