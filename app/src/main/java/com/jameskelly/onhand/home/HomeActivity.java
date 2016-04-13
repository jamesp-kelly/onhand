package com.jameskelly.onhand.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.di.HomeModule;
import com.jameskelly.onhand.di.OnHandApplication;
import com.jameskelly.onhand.util.NotImplementedException;
import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeView {

  @Inject
  HomePresenter presenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    ButterKnife.inject(this);

    OnHandApplication.getInstance(this)
        .getAppComponent()
        .plus(new HomeModule(this))
        .inject(this);

    //test dagger is working
    //presenter.openCamera();
  }

  @Override public void showPreviewImage(boolean confirmed) {
    throw new NotImplementedException();
  }

  @Override public void showPreviewError() {
    throw new NotImplementedException();
  }

  @Override public void showInitialButtons() {
    throw new NotImplementedException();
  }

  @Override public void showCamera() {
    throw new NotImplementedException();
  }

  @Override public void showGallery() {
    throw new NotImplementedException();
  }
}
