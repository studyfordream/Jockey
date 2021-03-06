package com.marverenic.music.instances.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;
import com.marverenic.music.R;

public class DraggableSongViewHolder extends SongViewHolder implements DraggableItemViewHolder {

    public ImageView dragHandle;
    public View itemView;
    private int flags = 0;

    public DraggableSongViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        dragHandle = (ImageView) itemView.findViewById(R.id.handle);
        dragHandle.setOnClickListener(null);
    }

    @Override
    public void setDragStateFlags(int i) {
        flags = i;
    }

    @Override
    public int getDragStateFlags() {
        return flags;
    }
}
