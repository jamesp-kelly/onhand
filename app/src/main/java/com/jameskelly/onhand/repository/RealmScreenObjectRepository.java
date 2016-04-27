package com.jameskelly.onhand.repository;

import android.content.Context;
import android.util.Log;
import com.jameskelly.onhand.model.ScreenObject;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import java.util.Iterator;
import java.util.List;

public class RealmScreenObjectRepository implements ScreenObjectRepository {

  private static final String TAG = RealmScreenObjectRepository.class.getSimpleName();

  private Context context;
  private Realm realm;
  private RealmConfiguration realmConfiguration;


  public RealmScreenObjectRepository(Context context) {
    this.context = context;
  }

  @Override public void setupConnection() {
    realmConfiguration = new RealmConfiguration.Builder(context).build();
    realm = Realm.getInstance(realmConfiguration);
  }

  @Override public void closeConnection() {
    if (realm != null && !realm.isClosed())
      realm.close();
  }

  @Override public ScreenObject getScreenObject(int screenObjectId) {
    return realm.where(ScreenObject.class).equalTo("id", screenObjectId).findFirst();
  }

  @Override public ScreenObject getActiveScreenObject() {
    return realm.where(ScreenObject.class).equalTo("isActive", true).findFirst();
  }

  @Override public List<ScreenObject> getAllPreviousScreenObjects() {
    return realm.where(ScreenObject.class).equalTo("isActive", false).findAll();
  }

  @Override public ScreenObject createScreenObject(String uriString, String message) {

    deactivateScreenObjects(); //clear any 'active' screenObjects. This new one will be the only active

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

  @Override public void detailScreenObjects() {
    RealmResults<ScreenObject> screenObjects = realm.allObjects(ScreenObject.class);

    for (ScreenObject object : screenObjects) {
      Log.i(TAG, String.format("%s - %b", object.getImageUriString(), object.isActive()));
    }
  }

  private void deactivateScreenObjects() {
    //cleaner way to do this?
    RealmResults<ScreenObject> activeScreenObjects =
        realm.where(ScreenObject.class).equalTo("isActive", true).findAll();

    if (activeScreenObjects.size() > 0) {
      realm.beginTransaction();
      for (Iterator<ScreenObject> i = activeScreenObjects.iterator(); i.hasNext();) {
        ScreenObject screenObject = i.next();
        screenObject.setActive(false);
      }
      realm.commitTransaction();
    }


  }
}
