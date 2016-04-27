package com.jameskelly.onhand.di;

import com.jameskelly.onhand.archive.ArchiveActivity;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ArchiveModule.class})
public interface ArchiveComponent {
  void inject(ArchiveActivity archiveActivity);
}
