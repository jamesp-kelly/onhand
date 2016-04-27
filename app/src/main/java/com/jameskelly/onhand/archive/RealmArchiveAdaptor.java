package com.jameskelly.onhand.archive;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.jameskelly.onhand.model.ScreenObject;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import java.util.List;

public class RealmArchiveAdaptor extends RealmBaseAdapter<ScreenObject> implements ListAdapter {

  private List<ScreenObject> realmResults;

  private static class ArchiveViewHolder {
    TextView name;
  }

  public RealmArchiveAdaptor(Context context, RealmResults<ScreenObject> realmResults,
      boolean automaticUpdate) {
    super(context, realmResults, automaticUpdate);
    this.realmResults = realmResults;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ArchiveViewHolder viewHolder;

    if (convertView == null) {
      convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
      viewHolder = new ArchiveViewHolder();
      viewHolder.name = (TextView) convertView.findViewById(android.R.id.text1);
      convertView.setTag(viewHolder);
      } else {
      viewHolder = (ArchiveViewHolder) convertView.getTag();
    }

    ScreenObject screenObject = realmResults.get(position);
    viewHolder.name.setText(screenObject.getImageUriString());

    return convertView;
  }
}
