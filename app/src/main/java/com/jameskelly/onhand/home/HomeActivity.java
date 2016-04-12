package com.jameskelly.onhand.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.util.NotImplementedException;

public class HomeActivity extends AppCompatActivity implements HomeView {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
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
