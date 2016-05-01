package com.jameskelly.onhand.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.di.LockScreenModule;
import com.jameskelly.onhand.di.OnHandApplication;
import com.jameskelly.onhand.model.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import javax.inject.Inject;

public class LockScreenActivity extends AppCompatActivity implements LockScreenView {

  private RequestCreator requestCreator;

  @BindView(R.id.lockscreen_image) ImageView lockscreenImage;

  @Inject ImageLoader imageLoader;
  @Inject LockScreenPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lockscreen);

    IntentFilter filter = new IntentFilter();
    filter.addAction(getString(R.string.finish_lockscreen_activity));
    registerReceiver(lockScreenReciever, filter);

    bindDI();
    setupWindow();

    Uri loadedImage = presenter.loadUriFromPreferences(
        getString(R.string.shared_prefs_saved_image));

    //todo update to use imageloader and redo a lot here
    Picasso picasso = Picasso.with(this);
    picasso.setIndicatorsEnabled(true);
    requestCreator = picasso.load(loadedImage).resize(1920, 1920).centerInside();
    requestCreator.into(lockScreenTarget);



    //temp
    lockscreenImage.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  private Target lockScreenTarget = new Target() {
    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      requestCreator.into(lockscreenImage);
    }

    @Override public void onBitmapFailed(Drawable errorDrawable) {}

    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {}
  };

  //set flags so window appears over lock screen
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

  //todo remove images unused after picasso
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
