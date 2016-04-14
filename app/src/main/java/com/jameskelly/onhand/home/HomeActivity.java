package com.jameskelly.onhand.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.di.HomeModule;
import com.jameskelly.onhand.di.OnHandApplication;
import com.jameskelly.onhand.util.NotImplementedException;
import java.io.File;
import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeView {

  private static final int TAKE_PICTURE_REQUEST_CODE = 1;
  private static final int LOAD_GALLERY_REQUEST_CODE = 2;

  private Uri imageUri;

  @Bind(R.id.image_preview) ImageView imagePreview;

  @Inject
  HomePresenter presenter;


  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    ButterKnife.bind(this);

    OnHandApplication.getInstance(this)
        .getAppComponent()
        .plus(new HomeModule(this))
        .inject(this);
  }

  @OnClick(R.id.camera_button)
  public void cameraClicked() {
    presenter.openCamera();
  }

  @OnClick(R.id.gallery_button)
  public void galleryClicked() {
    presenter.openGallery();
  }

  @Override public void showPreviewImage(Bitmap previewBitmap) {
    imagePreview.setImageBitmap(previewBitmap);
  }

  @Override public void showPreviewError() {
    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
  }

  @Override public void showInitialButtons() {
    throw new NotImplementedException();
  }

  @Override public void showCamera() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    File photo = new File(Environment.getExternalStorageDirectory(), "ON_HAND_CAPTURE.jpg");
    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    imageUri = Uri.fromFile(photo);
    startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
  }

  @Override public void showGallery() {
    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(intent, LOAD_GALLERY_REQUEST_CODE);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case TAKE_PICTURE_REQUEST_CODE: {
        if (resultCode == Activity.RESULT_OK) {
          presenter.loadPreviewImage(imageUri, getContentResolver());
        }
        break;
      }
      case LOAD_GALLERY_REQUEST_CODE: {
        if (resultCode == Activity.RESULT_OK) {
          presenter.loadPreviewImage(data.getData(), getContentResolver());
        }
        break;
      }
    }
  }
}
