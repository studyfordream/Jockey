package com.marverenic.music.activity.instance;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.marverenic.music.Library;
import com.marverenic.music.R;
import com.marverenic.music.activity.BaseActivity;
import com.marverenic.music.instances.Playlist;
import com.marverenic.music.instances.Song;
import com.marverenic.music.instances.viewholder.DraggableSongViewHolder;
import com.marverenic.music.utils.Themes;
import com.marverenic.music.view.BackgroundDecoration;
import com.marverenic.music.view.DividerDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class PlaylistActivity extends BaseActivity {

    public static final String PLAYLIST_EXTRA = "playlist";
    private ArrayList<Song> data;
    private Playlist reference;
    private Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance);

        reference = getIntent().getParcelableExtra(PLAYLIST_EXTRA);
        data = Library.getPlaylistEntries(this, reference);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(reference.playlistName);

        RecyclerView songRecyclerView = (RecyclerView) findViewById(R.id.list);

        RecyclerViewDragDropManager dragDropManager = new RecyclerViewDragDropManager();
        this.adapter = new Adapter();
        RecyclerView.Adapter adapter = dragDropManager.createWrappedAdapter(this.adapter);
        // TODO dragDropManager.setDraggingItemShadowDrawable((NinePatchDrawable) getResources().getDrawable(R.drawable.material_shadow_z3));

        songRecyclerView.setAdapter(adapter);
        songRecyclerView.addItemDecoration(new BackgroundDecoration(Themes.getBackgroundElevated()));
        songRecyclerView.addItemDecoration(new DividerDecoration(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songRecyclerView.setLayoutManager(layoutManager);

        songRecyclerView.addItemDecoration(new BackgroundDecoration(Themes.getBackgroundElevated()));
        songRecyclerView.addItemDecoration(new DividerDecoration(this).disableExtraPadding());
        // Padding is disabled because for some reason these views already get 1dp of spacing...

        dragDropManager.attachRecyclerView(songRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_playlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final ArrayList<Song> unsortedData = new ArrayList<>(data);
        switch (item.getItemId()){
            case R.id.action_sort_name:
                if (reference != null){
                    Library.sortSongList(data);
                    Library.editPlaylist(this, reference, data);

                    adapter.notifyDataSetChanged();

                    Snackbar
                            .make(
                                    findViewById(R.id.list),
                                    String.format(getResources().getString(R.string.message_sorted_playlist_name), reference),
                                    Snackbar.LENGTH_LONG)
                            .setAction(
                                    getResources().getString(R.string.action_undo),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            data = unsortedData;
                                            Library.editPlaylist(PlaylistActivity.this, reference, unsortedData);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                            .show();
                }
                return true;
            case R.id.action_sort_artist:
                if (reference != null){
                    Comparator<Song> artistComparator = new Comparator<Song>() {
                        @Override
                        public int compare(Song o1, Song o2) {
                            String o1c = o1.artistName.toLowerCase(Locale.ENGLISH);
                            String o2c = o2.artistName.toLowerCase(Locale.ENGLISH);
                            if (o1c.startsWith("the ")) {
                                o1c = o1c.substring(4);
                            } else if (o1c.startsWith("a ")) {
                                o1c = o1c.substring(2);
                            }
                            if (o2c.startsWith("the ")) {
                                o2c = o2c.substring(4);
                            } else if (o2c.startsWith("a ")) {
                                o2c = o2c.substring(2);
                            }
                            if (!o1c.matches("[a-z]") && o2c.matches("[a-z]")) {
                                return o2c.compareTo(o1c);
                            }
                            return o1c.compareTo(o2c);
                        }
                    };
                    Collections.sort(data, artistComparator);

                    Library.editPlaylist(this, reference, data);
                    adapter.notifyDataSetChanged();

                    Snackbar
                            .make(
                                    findViewById(R.id.list),
                                    String.format(getResources().getString(R.string.message_sorted_playlist_artist), reference),
                                    Snackbar.LENGTH_LONG)
                            .setAction(
                                    getResources().getString(R.string.action_undo),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            data = unsortedData;
                                            Library.editPlaylist(PlaylistActivity.this, reference, unsortedData);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                            .show();
                }
                return true;
            case R.id.action_sort_album:
                if (reference != null){
                    Comparator<Song> albumComparator = new Comparator<Song>() {
                        @Override
                        public int compare(Song o1, Song o2) {
                            String o1c = o1.albumName.toLowerCase(Locale.ENGLISH);
                            String o2c = o2.albumName.toLowerCase(Locale.ENGLISH);
                            if (o1c.startsWith("the ")) {
                                o1c = o1c.substring(4);
                            } else if (o1c.startsWith("a ")) {
                                o1c = o1c.substring(2);
                            }
                            if (o2c.startsWith("the ")) {
                                o2c = o2c.substring(4);
                            } else if (o2c.startsWith("a ")) {
                                o2c = o2c.substring(2);
                            }
                            if (!o1c.matches("[a-z]") && o2c.matches("[a-z]")) {
                                return o2c.compareTo(o1c);
                            }
                            return o1c.compareTo(o2c);
                        }
                    };
                    Collections.sort(data, albumComparator);

                    Library.editPlaylist(this, reference, data);
                    adapter.notifyDataSetChanged();

                    Snackbar
                            .make(
                                    findViewById(R.id.list),
                                    String.format(getResources().getString(R.string.message_sorted_playlist_album), reference),
                                    Snackbar.LENGTH_LONG)
                            .setAction(
                                    getResources().getString(R.string.action_undo),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            data = unsortedData;
                                            Library.editPlaylist(PlaylistActivity.this, reference, unsortedData);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                            .show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class Adapter extends RecyclerView.Adapter<DraggableSongViewHolder> implements DraggableItemAdapter<DraggableSongViewHolder> {

        public Adapter(){
            setHasStableIds(true);
        }

        @Override
        public DraggableSongViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            DraggableSongViewHolder viewHolder = new DraggableSongViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.instance_song_drag, viewGroup, false));
            viewHolder.setSongList(data);
            return viewHolder;
        }

        @Override
        public long getItemId(int position){
            return data.get(position).songId;
        }

        @Override
        public void onBindViewHolder(DraggableSongViewHolder viewHolder, int i) {
            viewHolder.update(data.get(i));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public boolean onCheckCanStartDrag(DraggableSongViewHolder viewHolder, int position, int x, int y){
            final View containerView = viewHolder.itemView;
            final View dragHandleView = viewHolder.dragHandle;

            final int offsetX =(int) (ViewCompat.getTranslationX(containerView) + 0.5f);

            final int tx = (int) (ViewCompat.getTranslationX(dragHandleView) + 0.5f);
            final int left = dragHandleView.getLeft() + tx;
            final int right = dragHandleView.getRight() + tx;

            return (x - offsetX >= left) && (x - offsetX <= right);
        }

        @Override
        public ItemDraggableRange onGetItemDraggableRange(DraggableSongViewHolder songViewHolder, int position) {
            return null;
        }

        @Override
        public void onMoveItem(int from, int to) {
            if (from == to) return;

            data.add(to, data.remove(from));
            Library.editPlaylist(PlaylistActivity.this, reference, data);
        }
    }

}
