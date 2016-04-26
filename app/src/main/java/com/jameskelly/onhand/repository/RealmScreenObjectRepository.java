package com.jameskelly.onhand.repository;

import android.content.Context;
import com.jameskelly.onhand.model.ScreenObject;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmScreenObjectRepository implements ScreenObjectRepository {

  private Context context;
  private Realm realm;
  private RealmConfiguration realmConfiguration;


  public RealmScreenObjectRepository(Context context) {
    this.context = context;
    realmConfiguration = new RealmConfiguration.Builder(context).build();
    realm = Realm.getInstance(realmConfiguration);
  }

  @Override public ScreenObject getScreenObject(int screenObjectId) {
    return realm.where(ScreenObject.class).equalTo("id", screenObjectId).findFirst();
  }

  @Override public void saveScreenObject(ScreenObject screenObject) {
    //unused for now
  }

  @Override public ScreenObject createScreenObject(String uriString, String message) {

    realm.beginTransaction();
    ScreenObject screenObject = realm.createObject(ScreenObject.class);
    int key;
    try {
      key = realm.where(ScreenObject.class).max("id").intValue() + 1;
    } catch (ArrayIndexOutOfBoundsException ex) {
      key = 0;
    }
    screenObject.setId(key);
    screenObject.setActive(true); //check for other active items and inactivate them
    screenObject.setImageUriString(uriString);
    screenObject.setMessage(message);
    screenObject.setCreatedTimeStamp(System.currentTimeMillis());

    realm.commitTransaction();

    return screenObject;
  }

  @Override public void closeConnection() {
    if (realm != null && !realm.isClosed())
    realm.close();
  }

  @Override protected void finalize() throws Throwable {
    super.finalize();
    closeConnection(); //todo: better way to close realm needed
  }
}
