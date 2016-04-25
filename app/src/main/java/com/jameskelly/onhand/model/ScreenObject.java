package com.jameskelly.onhand.model;

import android.net.Uri;

public class ScreenObject {
  private Uri uri;
  //private int id; todo: store previously used screens

  public ScreenObject(Uri uri) {
    this.uri = uri;
  }

  public Uri getUri() {
    return uri;
  }

  public void setUri(Uri uri) {
    this.uri = uri;
  }
}
