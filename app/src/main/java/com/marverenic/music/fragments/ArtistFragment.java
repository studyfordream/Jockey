package com.marverenic.music.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marverenic.music.Library;
import com.marverenic.music.R;
import com.marverenic.music.instances.viewholder.ArtistViewHolder;
import com.marverenic.music.utils.Themes;
import com.marverenic.music.view.BackgroundDecoration;
import com.marverenic.music.view.DividerDecoration;

public class ArtistFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);
        RecyclerView artistRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        artistRecyclerView.addItemDecoration(new BackgroundDecoration(Themes.getBackgroundElevated()));
        artistRecyclerView.addItemDecoration(new DividerDecoration(getActivity()));

        int paddingH =(int) getActivity().getResources().getDimension(R.dimen.global_padding);
        view.setPadding(paddingH, 0, paddingH, 0);

        artistRecyclerView.setAdapter(new Adapter());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        artistRecyclerView.setLayoutManager(layoutManager);

        return view;
    }


    public class Adapter extends RecyclerView.Adapter<ArtistViewHolder>{

        @Override
        public ArtistViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.instance_artist, viewGroup, false);
            return new ArtistViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ArtistViewHolder viewHolder, int i) {
            viewHolder.update(Library.getArtists().get(i));
        }

        @Override
        public int getItemCount() {
            return Library.getArtists().size();
        }
    }

}
