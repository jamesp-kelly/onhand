package com.jameskelly.onhand.model;

import io.realm.RealmObject;

public class ScreenObject extends RealmObject {

  private long id;
  private String imageUriString; //Uri type is unsupported by Realm
  private long createdTimeStamp;
  private boolean isActive;
  private String message;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getImageUriString() {
    return imageUriString;
  }

  public void setImageUriString(String imageUriString) {
    this.imageUriString = imageUriString;
  }

  public long getCreatedTimeStamp() {
    return createdTimeStamp;
  }

  public void setCreatedTimeStamp(long createdTimeStamp) {
    this.createdTimeStamp = createdTimeStamp;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
