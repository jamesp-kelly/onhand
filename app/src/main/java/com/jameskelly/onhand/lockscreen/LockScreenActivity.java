package com.jameskelly.onhand.lockscreen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.di.LockScreenModule;
import com.jameskelly.onhand.di.OnHandApplication;
import javax.inject.Inject;

public class LockScreenActivity extends AppCompatActivity implements LockScreenView {

  @Bind(R.id.lockscreen_image) ImageView lockscreenImage;

  @Inject LockScreenPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lockscreen);

    bindDI();
    setupWindow();

    presenter.loadSavedImageFromPrefs();
  }

  //set flags so window appears over lockscreen
  @Override public void setupWindow() {
    this.getWindow().setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN |
        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
  }

  @Override public void bindDI() {
    ButterKnife.bind(this);
    OnHandApplication.getInstance(this)
        .getAppComponent()
        .plus(new LockScreenModule(this))
        .inject(this);
  }

  @Override public void showImage(Bitmap savedImage) {
    lockscreenImage.setImageBitmap(savedImage);
  }

  @Override public void hideWindow() {
    finish();
  }
}
