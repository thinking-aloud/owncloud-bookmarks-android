package lkarsten.de.owncloudbookmarks;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lkarsten.de.owncloudbookmarks.util.BookmarkRetriever;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String BASE_URL = "https://lkarsten.de/owncloud/index.php/apps/bookmarks/public/rest/v1/bookmark";
        final String request = "?user=test&password=test";

        try {
            // get bookmarks
            final String response = new BookmarkRetriever().execute(BASE_URL + request).get();

            // convert to json
            final JSONArray json = new JSONArray(response);

            // Json to list
            final List<String> list = jsonToList(json);


            // get from layout
            final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            final ListView listView = (ListView) findViewById(R.id.listView);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.animate().setDuration(2000).alpha(0)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        list.remove(item);
                                        adapter.notifyDataSetChanged();
                                        view.setAlpha(1);
                                    }
                                });
                    }
                }

            });


        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    private List<String> jsonToList(JSONArray json) throws JSONException {
        final ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject object = json.getJSONObject(i);
            list.add(object.get("url").toString());
        }
        return list;
    }


}
