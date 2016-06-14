package com.github.keeninvye.soundstack;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keeninvye on 6/14/16.
 */
public class PlaylistFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    View rootView;
    LayoutInflater searchInflater;
    ListView playlist_listview;
    public List<Song> Playlist = new ArrayList<Song>();


    public PlaylistFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchInflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
        playlist_listview = (ListView) rootView.findViewById(R.id.playlist_listview);

        return rootView;
    }

    public void addSong(String name, String stream, Bitmap artwork){
        Playlist.add(new Song(name, stream, artwork));
    }

    public void addSong(Song song){
        Playlist.add(song);
    }

    public void refreshPlaylist() {
        ArrayAdapter<Song> adapter = new SongAdapter();
        playlist_listview.setAdapter(adapter);
        Log.d("SEARCH", Playlist.toString());
    }

    public class SongAdapter extends ArrayAdapter<Song> {
        public SongAdapter(){
            super(getActivity(), R.layout.listview_song, Playlist);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null){
                view = searchInflater.inflate(R.layout.listview_song, parent, false);
            }
            Song currentSong = Playlist.get(position);
            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView artwork = (ImageView) view.findViewById(R.id.artwork);
            title.setText(currentSong.getName());
            artwork.setImageBitmap(currentSong.getArtwork());
            Log.d("SEARCH", currentSong.getName());

            return view;
        }
    }
}
