package com.jameskelly.onhand.lockscreen;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.jameskelly.onhand.R;
import java.io.IOException;

public class LockScreenActivity extends AppCompatActivity implements LockScreenView {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);


    //testing
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    //


    Button set = (Button) findViewById(R.id.set_button);
    Button clear = (Button) findViewById(R.id.clear_button);

    final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);

    set.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //try {
        // wallpaperManager.setResource(R.raw.test_wallpaper);
        //} catch (IOException e) {
        //  e.printStackTrace();
        //}
      }
    });

    clear.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        try {
          wallpaperManager.clear();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
