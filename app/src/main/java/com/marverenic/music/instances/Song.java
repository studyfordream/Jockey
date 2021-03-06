package com.marverenic.music.instances;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String songName;
    public int songId;
    public String artistName;
    public String albumName;
    public int songDuration;
    public String location;
    public int albumId;
    public int artistId;
    public int genreId = -1;
    public int trackNumber = 0;
    public int playCount = 0;
    public int skipCount = 0;

    public Song(final String songName, final int songId, final String artistName,
                final String albumName, final int songDuration, final String location,
                final int albumId, final int artistId) {

        this.songName = songName;
        this.songId = songId;
        this.artistName = artistName;
        this.albumName = albumName;
        this.songDuration = songDuration;
        this.location = location;
        this.albumId = albumId;
        this.artistId = artistId;
    }

    private Song(Parcel in) {
        songName = in.readString();
        songId = in.readInt();
        albumName = in.readString();
        artistName = in.readString();
        songDuration = in.readInt();
        location = in.readString();
        albumId = in.readInt();
        artistId = in.readInt();
        genreId = in.readInt();
        playCount = in.readInt();
        skipCount = in.readInt();
    }

    public Song(Song s) {
        this.songName = s.songName;
        this.songId = s.songId;
        this.artistName = s.artistName;
        this.albumName = s.albumName;
        this.songDuration = s.songDuration;
        this.location = s.location;
        this.albumId = s.albumId;
        this.artistId = s.artistId;
        this.genreId = s.genreId;
        this.trackNumber = s.trackNumber;
        this.playCount = s.playCount;
        this.skipCount = s.skipCount;
    }

    public boolean equals(final Object obj) {
        return this == obj ||
                (obj != null && obj instanceof Song && songId == ((Song) obj).songId);
    }

    public String toString() {
        return songName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeInt(songId);
        dest.writeString(albumName);
        dest.writeString(artistName);
        dest.writeInt(songDuration);
        dest.writeString(location);
        dest.writeInt(albumId);
        dest.writeInt(artistId);
        dest.writeInt(genreId);
        dest.writeInt(playCount);
        dest.writeInt(skipCount);
    }
}
