package com.jameskelly.onhand.archive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.di.ArchiveModule;
import com.jameskelly.onhand.di.OnHandApplication;
import com.jameskelly.onhand.model.ScreenObject;
import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;

public class ArchiveActivity extends AppCompatActivity implements ArchiveView {

  private ArchiveRecyclerViewAdapter archiveRecyclerViewAdapter;

  @BindView(R.id.archive_recycler_view) RealmRecyclerView archiveRecyclerView;

  @Inject ArchivePresenter presenter;

  public static Intent newIntent(Context context) {
    return new Intent(context, ArchiveActivity.class);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_archive);
    bindDI();
    presenter.onViewCreated();
    presenter.loadScreenObjects();
  }

  @Override public void displayScreenObjects(List<ScreenObject> screenObjects) {

    archiveRecyclerViewAdapter = new ArchiveRecyclerViewAdapter(
        this, (RealmResults<ScreenObject>) screenObjects, true, false);
    archiveRecyclerView.setAdapter(archiveRecyclerViewAdapter);
  }

  @Override public void bindDI() {
    ButterKnife.bind(this);
    OnHandApplication.getInstance(this)
        .getAppComponent()
        .plus(new ArchiveModule(this))
        .inject(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onViewDestroyed();
  }
}
