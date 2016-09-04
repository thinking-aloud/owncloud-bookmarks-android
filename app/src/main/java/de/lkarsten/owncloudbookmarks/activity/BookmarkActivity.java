package de.lkarsten.owncloudbookmarks.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.lkarsten.owncloudbookmarks.dto.Bookmark;
import de.lkarsten.owncloudbookmarks.rest.BookmarkDownloader;
import de.lkarsten.owncloudbookmarks.util.BookmarkAdapter;
import lkarsten.de.owncloudbookmarks.R;

public class BookmarkActivity extends AppCompatActivity {

    public static BookmarkAdapter adapter;

    public static final String PREFS_NAME = "ownCloudBookmarkPrefs";

    String server_url;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        loadCredentials();

        final String BASE_URL = server_url + "/index.php/apps/bookmarks/public/rest/v1/bookmark";
        final String request = "?user=" + username + "&password=" + password;

        System.out.println("BASE_URL: " + BASE_URL);
        System.out.println("request: " + request);

        try {
            URL url = new URL(BASE_URL + request);
            showBookmarks(url);
        } catch (InterruptedException | ExecutionException | JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                System.out.println("sync clicked");
                return true;
            case R.id.settings:
                showSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadCredentials() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        server_url = settings.getString("server_url", "");
        username = settings.getString("username", "");
        password = settings.getString("password", "");
    }

    private void showSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void showBookmarks(URL url) throws InterruptedException, ExecutionException, JSONException, MalformedURLException {
        final String response = new BookmarkDownloader().execute(url).get();

        final JSONArray json = new JSONArray(response);

        final ArrayList<Bookmark> bookmarks = jsonToArrayList(json);

        adapter = new BookmarkAdapter(this, bookmarks);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Uri uri = Uri.parse(bookmarks.get(position).getUrl().toString());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browserIntent);
            }

        });
    }

    private ArrayList<Bookmark> jsonToArrayList(JSONArray json) throws JSONException, MalformedURLException, ExecutionException, InterruptedException {
        final ArrayList<Bookmark> list = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            Bookmark bookmark = new Bookmark(object);
            list.add(bookmark);
        }
        return list;
    }

}
