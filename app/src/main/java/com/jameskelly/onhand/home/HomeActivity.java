package com.jameskelly.onhand.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.archive.ArchiveActivity;
import com.jameskelly.onhand.di.HomeModule;
import com.jameskelly.onhand.di.OnHandApplication;
import com.jameskelly.onhand.service.OnHandServiceImpl;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeView {

  private static final int TAKE_PICTURE_REQUEST_CODE = 1;
  private static final int LOAD_GALLERY_REQUEST_CODE = 2;

  private Uri imageUri;
  private RequestCreator requestCreator;
  private boolean loadFromCamera = false;
  private File picturesOutputFolder;
  private final DateFormat imageNameFormat =
      new SimpleDateFormat("'OnHand_'yyyy-MM-dd-HH-mm-ss'.jpeg'", Locale.US);

  @BindView(R.id.image_preview) ImageView imagePreview;
  @BindView(R.id.start_service_fab) FloatingActionButton startServiceFab;

  @Inject HomePresenter presenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    bindDI();

    presenter.onViewCreated();
    picturesOutputFolder = new File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "OnHand");
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

  @OnClick(R.id.start_service_fab)
  public void startServiceClicked() {
    //toggleOnHandService();


    //todo: testing. revert asap
    navigateToArchive();
  }

  @Override public void showPreviewImage(final String imageUriString, boolean skipCacheLookup) {
    Uri imageUri = Uri.parse(imageUriString);
    Picasso picasso = Picasso.with(this);
    picasso.setIndicatorsEnabled(true);
    requestCreator = picasso.load(imageUri)
        .resize(1920, 1920).centerInside(); //centerInside means the ratio will be kept with largest dimension 1920

    if (skipCacheLookup) {
      requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE)
          .networkPolicy(NetworkPolicy.NO_CACHE);
    }
    requestCreator.into(imagePreviewTarget);

    startServiceFab.setVisibility(View.VISIBLE);
  }

  private Target imagePreviewTarget = new Target() {
    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      requestCreator.into(imagePreview);
    }

    @Override public void onBitmapFailed(Drawable errorDrawable) {
      showPreviewError();
    }
    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {}
  };

  @Override public void showPreviewError() {
    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
  }

  @Override public void startCamera() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    String fileName = imageNameFormat.format(new Date());
    File photo = new File(picturesOutputFolder, fileName);
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
