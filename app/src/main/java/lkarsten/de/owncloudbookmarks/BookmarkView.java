package lkarsten.de.owncloudbookmarks;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lkarsten.de.owncloudbookmarks.util.Bookmark;
import lkarsten.de.owncloudbookmarks.util.BookmarkRetriever;

public class BookmarkView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarkview);

        final String BASE_URL = "https://lkarsten.de/owncloud/index.php/apps/bookmarks/public/rest/v1/bookmark";
//        final String request = "?user=test&password=test&select[]=description&select[]=tags";
        final String request = "?user=test&password=test";

        try {
            // get bookmarks
            final String response = new BookmarkRetriever().execute(BASE_URL + request).get();

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
                    int pos = position + 1;
                    Toast.makeText(BookmarkView.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
                }

            });

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

    }

    private List<Bookmark> jsonToList(JSONArray json) throws JSONException {
        final List<Bookmark> list = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            list.add(new Bookmark(object));
        }
        return list;
    }

}