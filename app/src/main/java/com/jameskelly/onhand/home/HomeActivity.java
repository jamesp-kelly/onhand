package com.jameskelly.onhand.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.archive.ArchiveActivity;
import com.jameskelly.onhand.di.HomeModule;
import com.jameskelly.onhand.di.OnHandApplication;
import com.jameskelly.onhand.model.ImageLoader;
import com.jameskelly.onhand.service.OnHandServiceImpl;
import com.jameskelly.onhand.util.OnHandUtils;
import com.squareup.picasso.RequestCreator;
import java.io.File;
import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeView {

  private static final int TAKE_PICTURE_REQUEST_CODE = 1;
  private static final int LOAD_GALLERY_REQUEST_CODE = 2;

  private Uri imageUri;
  private RequestCreator requestCreator;
  private boolean loadFromCamera = false;

  @BindView(R.id.image_preview) ImageView imagePreview;
  @BindView(R.id.add_content) FloatingActionButton bigAddFab;
  @BindView(R.id.add_camera) FloatingActionButton cameraFab;
  @BindView(R.id.add_gallery) FloatingActionButton galleryFab;
  @BindView(R.id.add_link) FloatingActionButton linkFab;

  @Inject ImageLoader imageLoader;
  @Inject HomePresenter presenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    bindDI();

    presenter.onViewCreated();
  }

  @Override protected void onPause() {
    super.onPause();
    loadFromCamera = false;
  }

  @Override protected void onResume() {
    super.onResume();
    if (!loadFromCamera) {
      presenter.loadActiveScreenObject();
    }
  }

  @Override public void bindDI() {
    ButterKnife.bind(this);
    OnHandApplication.getInstance(this)
        .getAppComponent()
        .plus(new HomeModule(this))
        .inject(this);
  }

  @OnClick(R.id.camera_button)
  public void cameraClicked() {
    startCamera();
  }

  @OnClick(R.id.gallery_button)
  public void galleryClicked() {
    startGallery();
  }

  @OnClick(R.id.add_content)
  public void addClicked(View v) {
    if (cameraFab.getVisibility() == View.VISIBLE) {
      cameraFab.hide();
      galleryFab.hide();
      linkFab.hide();
      ViewCompat.animate(bigAddFab).rotation(0f).withLayer().setDuration(300)
          .setInterpolator(new OvershootInterpolator(3f)).start();
    } else {
      cameraFab.show();
      galleryFab.show();
      linkFab.show();
      ViewCompat.animate(bigAddFab).rotation(135f).withLayer().setDuration(300)
          .setInterpolator(new OvershootInterpolator(3f)).start();

    }
  }

  @OnClick(R.id.add_camera)
  public void addCameraClicked(View v) {
    startCamera();
  }

  @OnClick(R.id.add_gallery)
  public void addGalleryClicked(View v) {
    startGallery();
  }

  @OnClick(R.id.add_link)
  public void addLinkClicked(View v) {
    Toast.makeText(this, "Do something here", Toast.LENGTH_SHORT).show();
  }

  @Override public void updatePreviewImage(Bitmap bitmap) {
    imagePreview.setImageBitmap(bitmap);
  }

  @Override public void showPreviewError() {
    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
  }

  @Override public void startCamera() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    File photo = OnHandUtils.createNewImageFile();
    imageUri = Uri.fromFile(photo);

    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
  }

  @Override public void startGallery() {
    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(intent, LOAD_GALLERY_REQUEST_CODE);
  }

  @Override public void toggleOnHandService() {
    Intent intent = new Intent(this, OnHandServiceImpl.class);
    intent.putExtra("onHandImageUri", imageUri);

    if (OnHandServiceImpl.isServiceRunning(this)) {
      stopService(intent);
      //todo: update sharedPrefs/ScreenObject
    } else {
      startService(intent);
      //todo: update sharedPrefs/ScreenObject
    }
  }

  @Override public void navigateToArchive() {
    startActivity(ArchiveActivity.newIntent(this));
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case TAKE_PICTURE_REQUEST_CODE: {
        if (resultCode == Activity.RESULT_OK) {
          loadFromCamera = true;
          presenter.createScreenObject(imageUri.toString());
        }
        break;
      }
      case LOAD_GALLERY_REQUEST_CODE: {
        if (resultCode == Activity.RESULT_OK) {
          loadFromCamera = true;
          presenter.createScreenObject(data.getDataString());
        }
        break;
      }
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onViewDestroyed();
  }
}
