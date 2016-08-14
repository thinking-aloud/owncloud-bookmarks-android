package lkarsten.de.owncloudbookmarks.util;


import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Bookmark {
    private String title;
    private URL url;
    private String description;
    private List<String> tags;
    private Bitmap favicon;

    public Bookmark(JSONObject object) throws JSONException, MalformedURLException, ExecutionException, InterruptedException {
        setTitle(object);
        setUrl(object);
        setDescription(object);
        setTags(object);
        favicon = new faviconDownloader().execute(url).get();
    }

    public void setTitle(JSONObject object) throws JSONException {
        title = object.getString("title");
    }

    public void setUrl(JSONObject object) throws JSONException, MalformedURLException {
        url = new URL(object.getString("url"));
    }

    public void setDescription(JSONObject object) throws JSONException {
        if (object.has("description") && !object.getString("description").equals("–[\"\"]")) {
            description = object.getString("description");
        } else {
            description = "";
        }
    }

    public void setTags(JSONObject object) throws JSONException {
        tags = new ArrayList<>();

        if (object.has("tags") && !object.getString("tags").equals("[\"\"]")) {
            JSONArray json = object.getJSONArray("tags");

            if (json != null) {


                int length = json.length();
                for (int i = 0; i < length; i++) {
                    tags.add(json.get(i).toString());
                }
            }

        }
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getTagsAsString() {
        if (tags.isEmpty()) {
            return "";
        } else {
            String tagStr = tags.toString();
            return tagStr.substring(1, tagStr.length() - 1);
        }
    }

    public Bitmap getFavicon() {
        return favicon;
    }
}
