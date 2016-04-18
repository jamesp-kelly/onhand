package com.jameskelly.onhand.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.di.HomeModule;
import com.jameskelly.onhand.di.OnHandApplication;
import com.jameskelly.onhand.service.OnHandServiceImpl;
import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeView {

  private static final int TAKE_PICTURE_REQUEST_CODE = 1;
  private static final int LOAD_GALLERY_REQUEST_CODE = 2;

  private Uri imageUri;

  @Bind(R.id.image_preview) ImageView imagePreview;
  @Bind(R.id.start_service_fab) FloatingActionButton startServiceFab;

  @Inject HomePresenter presenter;


  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    ButterKnife.bind(this);

    OnHandApplication.getInstance(this)
        .getAppComponent()
        .plus(new HomeModule(this))
        .inject(this);

    //if there's an image saved in SharedPreferences, display it
    presenter.loadPreviewImageFromPrefs();
  }

  @OnClick(R.id.camera_button)
  public void cameraClicked() {
    presenter.openCamera();
  }

  @OnClick(R.id.gallery_button)
  public void galleryClicked() {
    presenter.openGallery();
  }

  @OnClick(R.id.start_service_fab)
  public void startServiceClicked() {
    presenter.toggleService(!OnHandServiceImpl.isServiceRunning(this));
  }

  @Override public void showPreviewImage(Bitmap previewBitmap) {
    imagePreview.setImageBitmap(previewBitmap);
    startServiceFab.setVisibility(View.VISIBLE);
  }

  @Override public void showPreviewError() {
    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
  }

  @Override public void startCamera(Intent intent) {
    imageUri = intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
    startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
  }

  @Override public void startGallery(Intent intent) {
    startActivityForResult(intent, LOAD_GALLERY_REQUEST_CODE);
  }

  @Override public void startOnHandService(Intent intent) {
    startService(intent);
  }

  @Override public void stopOnHandService(Intent intent) {
    stopService(intent);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case TAKE_PICTURE_REQUEST_CODE: {
        if (resultCode == Activity.RESULT_OK) {
          presenter.loadPreviewImage(imageUri);
        }
        break;
      }
      case LOAD_GALLERY_REQUEST_CODE: {
        if (resultCode == Activity.RESULT_OK) {
          presenter.loadPreviewImage(data.getData());
        }
        break;
      }
    }
  }

  @Override protected void onPause() {
    super.onPause();
    Log.i(HomeActivity.class.getSimpleName(), "PAUSE");
  }
}
