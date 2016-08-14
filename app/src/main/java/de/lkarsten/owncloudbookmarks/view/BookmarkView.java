package de.lkarsten.owncloudbookmarks.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.lkarsten.owncloudbookmarks.rest.BookmarkDownloader;
import de.lkarsten.owncloudbookmarks.util.Bookmark;
import de.lkarsten.owncloudbookmarks.util.ListViewAdapter;
import lkarsten.de.owncloudbookmarks.R;

public class BookmarkView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarkview);

        final String BASE_URL = "https://lkarsten.de/owncloud/index.php/apps/bookmarks/public/rest/v1/bookmark";
        final String request = "?user=" + getString(R.string.username) + "&password=" + getString(R.string.password);

        try {
            URL url = new URL(BASE_URL + request);
            showBookmarks(url);
        } catch (InterruptedException | ExecutionException | JSONException | MalformedURLException e) {
            e.printStackTrace();
        }


    }

    private void showBookmarks(URL url) throws InterruptedException, ExecutionException, JSONException, MalformedURLException {
        // get bookmarks
        final String response = new BookmarkDownloader().execute(url).get();

        // convert to json
        final JSONArray json = new JSONArray(response);

        // Json to list
        final List<Bookmark> list = jsonToList(json);

        final ListView listView = (ListView) findViewById(R.id.listView);

        final ListViewAdapter adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Uri uri = Uri.parse(list.get(position).getUrl().toString());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browserIntent);
            }

        });
    }

    private List<Bookmark> jsonToList(JSONArray json) throws JSONException, MalformedURLException, ExecutionException, InterruptedException {
        final List<Bookmark> list = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            list.add(new Bookmark(object));
        }
        return list;
    }

}
