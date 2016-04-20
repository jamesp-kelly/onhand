package com.jameskelly.onhand.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    IntentFilter filter = new IntentFilter();
    filter.addAction(getString(R.string.finish_lockscreen_activity));
    registerReceiver(lockScreenReciever, filter);

    bindDI();
    setupWindow();

    Log.i("TAG", "now loading image from prefs");
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
    Log.i("TAG", "now setting image");
    lockscreenImage.setImageBitmap(savedImage);
  }

  @Override public void hideWindow() {
    finish();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
    unregisterReceiver(lockScreenReciever);
  }

  private final BroadcastReceiver lockScreenReciever = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals(getString(R.string.finish_lockscreen_activity))) {
        LockScreenActivity.this.finish();
      }
    }
  };
}
