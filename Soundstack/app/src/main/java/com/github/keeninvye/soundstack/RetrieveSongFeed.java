package com.github.keeninvye.soundstack;

import com.github.keeninvye.soundstack.SearchFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by keeninvye on 6/13/16.
 */
class RetrieveSongFeed extends AsyncTask<String, String, Void> {

    String query;
    Bitmap artwork;
    URL url;
    HttpURLConnection urlConnection;
    JSONObject jObj = null;
    JSONArray songs = null;
    SearchFragment searchFragment;

    public RetrieveSongFeed(SearchFragment searchFragment){
        super();
        this.searchFragment = searchFragment;
    }

    protected Void doInBackground(String... s) {
        Log.d("SEARCH", "doInBackground");
        query = s[0];
        try {

            url = new URL(String.format("https://api.soundcloud.com/search?q=%s&facet=model&limit=50&offset=0&linked_partitioning=1&client_id=b535f6d6f6bee7d19945dfa4cfa082a6", Uri.encode(query)));
            urlConnection = (HttpURLConnection) url.openConnection();
            StringBuilder result = new StringBuilder();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            jObj = new JSONObject(result.toString());

        }catch( Exception e) {
            Log.d("SEARCH", e.toString());
        }
        finally {
            urlConnection.disconnect();
        }
        createSongs(jObj);
        return null;
    }

    public JSONObject createSongs(JSONObject obj){
        Log.d("SEARCH", "createSongs");
        try {
            songs = obj.getJSONArray("collection");
            Log.d("SEARCH", "Trying to get Artwork");
            for(int i = 0; i < songs.length(); i++) {
                JSONObject c = songs.getJSONObject(i);
                if(c.getString("kind").equals("track") && c.has("stream_url") && !c.getString("artwork_url").equals("null")){
                    // Storing each json item in variable
                    String id = c.getString("id");
                    String title = c.getString("title");
                    String stream = c.getString("stream_url");
                    String artwork_url = c.getString("artwork_url");
                    try {
                        URL aURL = new URL(artwork_url);
                        URLConnection conn = aURL.openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        artwork = BitmapFactory.decodeStream(bis);
                        bis.close();
                        is.close();
                    } catch (IOException e) {
                        Log.e("SEARCH", "Error getting bitmap", e);
                    }
                    searchFragment.addSong(title, stream, artwork);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("SEARCH", "Populating Search");
        searchFragment.populateSearch();
    }
}