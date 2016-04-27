package com.jameskelly.onhand.di;

import android.app.Application;
import com.jameskelly.onhand.archive.ArchivePresenter;
import com.jameskelly.onhand.archive.ArchivePresenterImpl;
import com.jameskelly.onhand.archive.ArchiveView;
import com.jameskelly.onhand.repository.RealmScreenObjectRepository;
import com.jameskelly.onhand.repository.ScreenObjectRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ArchiveModule {
  final ArchiveView view;

  public ArchiveModule(ArchiveView view) {
    this.view = view;
  }

  @Provides
  @ActivityScope ArchiveView provideArchiveView() {
    return view;
  }

  //todo: move to AppModule
  @Provides
  @ActivityScope
  public ScreenObjectRepository provideScreenObjectRepository(Application application) {
    return new RealmScreenObjectRepository(application.getApplicationContext());
  }

  @Provides
  @ActivityScope ArchivePresenter provideArchivePresenter(ScreenObjectRepository repository) {
    return new ArchivePresenterImpl(view, repository); //todo: fix the way presenter gets view
  }
}
