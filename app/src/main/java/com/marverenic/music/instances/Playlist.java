package com.marverenic.music.instances;

import android.os.Parcel;
import android.os.Parcelable;

public class Playlist implements Parcelable {

    public static final Parcelable.Creator<Playlist> CREATOR = new Parcelable.Creator<Playlist>() {
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public int playlistId;
    public String playlistName;

    public Playlist(final int playlistId, final String playlistName) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
    }

    private Playlist(Parcel in) {
        playlistId = in.readInt();
        playlistName = in.readString();
    }

    public boolean equals(final Object obj) {
        return this == obj ||
                (obj != null && obj instanceof Playlist && playlistId == ((Playlist) obj).playlistId);
    }

    public String toString() {
        return playlistName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(playlistId);
        dest.writeString(playlistName);
    }
}
