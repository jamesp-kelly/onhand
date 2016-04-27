package com.jameskelly.onhand.archive;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.model.ScreenObject;
import com.squareup.picasso.Picasso;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class ArchiveRecyclerViewAdapter extends RealmBasedRecyclerViewAdapter<ScreenObject,
    ArchiveRecyclerViewAdapter.ViewHolder> {

  private final Context context;

  public ArchiveRecyclerViewAdapter(Context context, RealmResults<ScreenObject> realmResults,
      boolean autoUpdate, boolean animateResults) {

    super(context, realmResults, autoUpdate, animateResults);
    this.context = context;
  }

  public class ViewHolder extends RealmViewHolder {

    //@Bind(R.id.archive_item_image) ImageView archiveImageView;
    //@Bind(R.id.archive_item_message) TextView archiveTextView;

    ImageView archiveImageView;
    TextView archiveTextView;

    public ViewHolder(View container) {
      super(container);
      //ButterKnife.bind(context, container);

      archiveImageView = (ImageView) container.findViewById(R.id.archive_item_image);
      archiveTextView = (TextView) container.findViewById(R.id.archive_item_message);
    }
  }

  @Override public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
    View v = inflater.inflate(R.layout.archive_grid_item_view, viewGroup, false);
    return new ViewHolder(v);
  }

  @Override public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
    final ScreenObject screenObject = realmResults.get(i);
    Picasso.with(context).load(screenObject.getImageUriString()).into(viewHolder.archiveImageView);
    viewHolder.archiveTextView.setText("This is just a test");
  }

}