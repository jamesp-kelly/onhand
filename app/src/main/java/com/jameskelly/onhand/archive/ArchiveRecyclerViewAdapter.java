package com.jameskelly.onhand.archive;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.model.ImageLoader;
import com.jameskelly.onhand.model.ScreenObject;
import com.jameskelly.onhand.util.OnHandUtils;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class ArchiveRecyclerViewAdapter extends RealmBasedRecyclerViewAdapter<ScreenObject,
    ArchiveRecyclerViewAdapter.ViewHolder> {

  private final Context context;
  private final ImageLoader imageLoader;

  public ArchiveRecyclerViewAdapter(Context context, ImageLoader imageLoader,
      RealmResults<ScreenObject> realmResults, boolean autoUpdate, boolean animateResults) {

    super(context, realmResults, autoUpdate, animateResults);
    this.context = context;
    this.imageLoader = imageLoader;
  }

  public class ViewHolder extends RealmViewHolder {

    @BindView(R.id.archive_item_image) ImageView archiveImageView;
    @BindView(R.id.archive_note_text) TextView archiveNoteTextView;
    @BindView(R.id.archive_date_text) TextView archiveDateTextView;


    public ViewHolder(View container) {
      super(container);
      ButterKnife.bind(this, container);
    }
  }

  @Override public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
    View v = inflater.inflate(R.layout.archive_grid_item_view, viewGroup, false);
    return new ViewHolder(v);
  }

  @Override public void onBindRealmViewHolder(ViewHolder viewHolder, int i) {
    final ScreenObject screenObject = realmResults.get(i);
    imageLoader.loadImageForImageView(screenObject.getImageUriString(), viewHolder.archiveImageView, 800, true, false);

    String dateString = OnHandUtils.formattedDate(screenObject.getCreatedTimeStamp());
    viewHolder.archiveDateTextView.setText(dateString);

    if (screenObject.getMessage() != null && !screenObject.getMessage().isEmpty()) {
      viewHolder.archiveNoteTextView.setText(screenObject.getMessage());
    }
  }
}
