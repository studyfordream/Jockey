package com.marverenic.music.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.marverenic.music.R;
import com.marverenic.music.adapters.SongListAdapter;
import com.marverenic.music.instances.Song;
import com.marverenic.music.utils.Themes;

import java.util.ArrayList;

public class SongFragment extends Fragment {

    public ArrayList<Song> songLibrary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView songListView = (ListView) view.findViewById(R.id.list);

        SongListAdapter adapter;
        if (songLibrary == null) {
            adapter = new SongListAdapter(getActivity());
        } else {
            adapter = new SongListAdapter(songLibrary, getActivity());
        }

        songListView.setAdapter(adapter);
        songListView.setOnItemClickListener(adapter);
        songListView.setOnItemLongClickListener(adapter);

        Themes.themeFragment(R.layout.fragment_list, view, this);

        return view;
    }
}