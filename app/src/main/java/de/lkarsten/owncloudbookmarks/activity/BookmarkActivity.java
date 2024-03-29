package de.lkarsten.owncloudbookmarks.activity;


import android.content.Intent;
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
import de.lkarsten.owncloudbookmarks.dto.Credentials;
import de.lkarsten.owncloudbookmarks.rest.BookmarkDownloader;
import de.lkarsten.owncloudbookmarks.util.BookmarkAdapter;
import lkarsten.de.owncloudbookmarks.R;

public class BookmarkActivity extends AppCompatActivity {

    public static BookmarkAdapter adapter;

    String baseUrl;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        loadBookmarks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    private void loadCredentials() {
        Credentials credentials = Credentials.getInstance();

        baseUrl = credentials.getBaseUrl();
        username = credentials.getUsername();
        password = credentials.getPassword();
    }

    private void loadBookmarks() {
        loadCredentials();
        String requestUrl = buildRequestUrl();

        fetchBookmarks(requestUrl);
    }

    private void fetchBookmarks(String requestUrl) {
        try {
            URL url = new URL(requestUrl);
            showBookmarks(url);
        } catch (InterruptedException | ExecutionException | JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String buildRequestUrl() {
        final String BASE_URL = baseUrl + "/index.php/apps/bookmarks/public/rest/v1/bookmark";
        final String request = "?user=" + username + "&password=" + password;

        return BASE_URL + request;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                loadBookmarks();
                return true;
            case R.id.settings:
                showSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
