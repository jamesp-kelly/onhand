package com.jameskelly.onhand.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jameskelly.onhand.R;
import com.jameskelly.onhand.model.ScreenObject;
import com.jameskelly.onhand.util.OnHandUtils;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ArchiveListAdapter extends RecyclerView.Adapter<ArchiveListAdapter.ArchiveViewHolder> {

  private final Context context;
  private List<ScreenObject> screenObjects;

  public static class ArchiveViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.archive_item_image) ImageView archiveImageView;
    @BindView(R.id.archive_note_text) TextView archiveNoteTextView;
    @BindView(R.id.archive_date_text) TextView archiveDateTextView;

    public ArchiveViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public ArchiveListAdapter(Context context, List<ScreenObject> screenObjects) {
    this.screenObjects = screenObjects;
    this.context = context;
  }

  @Override public ArchiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.archive_grid_item_view, parent, false);

    return new ArchiveViewHolder(view);
  }

  @Override public void onBindViewHolder(ArchiveViewHolder holder, int position) {

    final ScreenObject screenObject = screenObjects.get(position);

    holder.archiveNoteTextView.setText(screenObject.getMessage());
    holder.archiveDateTextView.setText(OnHandUtils.formattedDate(screenObject.getCreatedTimeStamp()));

    Picasso.with(context).load("http://vignette3.wikia.nocookie.net/bobsburgerpedia/images/3/3e/Bobbelcher2.png")
        .resize(800, 800).centerInside().into(holder.archiveImageView);
  }

  @Override public int getItemCount() {
    return screenObjects.size();
  }
}
