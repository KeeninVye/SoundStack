package com.github.keeninvye.soundstack;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
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
 * Created by keeninvye on 6/13/16.
 */
public class SearchFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    View rootView;
    View itemView;
    public ListView searchview_listview;
    public List<Song> Songs = new ArrayList<Song>();

    public SearchFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_searchlist, container, false);
        searchview_listview = (ListView) rootView.findViewById(R.id.listview_search);
        itemView = inflater.inflate(R.layout.listview_song, container, false);

        return rootView;
    }

    public void addSong(String name, String stream, Bitmap artwork){
        Songs.add(new Song(name, stream, artwork));
    }

    public void populateSearch() {
        ArrayAdapter<Song> adapter = new SongAdapter();
        searchview_listview.setAdapter(adapter);
        Log.d("SEARCH", Songs.toString());
    }

    public class SongAdapter extends ArrayAdapter<Song> {
        public SongAdapter(){
            super(getActivity(), R.layout.listview_song, Songs);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null){
                view = itemView;
            }
            Song currentSong = Songs.get(position);
            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView artwork = (ImageView) view.findViewById(R.id.artwork);
            title.setText(currentSong.getName());
            artwork.setImageBitmap(currentSong.getArtwork());

            return view;
        }
    }
}
