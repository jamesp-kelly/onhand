package com.jameskelly.onhand.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jameskelly.onhand.R;
import java.util.List;

public class ArchiveListAdapter extends RecyclerView.Adapter<ArchiveListAdapter.ArchiveViewHolder> {

  private List<String> strings;

  public static class ArchiveViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.my_textview) TextView textView;

    public ArchiveViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public ArchiveListAdapter(List<String> strings) {
    this.strings = strings;
  }

  @Override public ArchiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.archive_temp_list_item, parent, false);

    return new ArchiveViewHolder(view);
  }

  @Override public void onBindViewHolder(ArchiveViewHolder holder, int position) {
    holder.textView.setText(strings.get(position));
  }

  @Override public int getItemCount() {
    return strings.size();
  }
}
