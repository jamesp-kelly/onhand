package com.jameskelly.onhand.lockscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import butterknife.ButterKnife;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.di.LockScreenModule;
import com.jameskelly.onhand.di.OnHandApplication;

public class LockScreenActivity extends AppCompatActivity implements LockScreenView {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    ButterKnife.bind(this);

    OnHandApplication.getInstance(this)
        .getAppComponent()
        .plus(new LockScreenModule(this))
        .inject(this);


    //testing
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    //
  }
}
