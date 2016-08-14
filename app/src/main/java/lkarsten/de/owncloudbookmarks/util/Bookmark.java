package lkarsten.de.owncloudbookmarks.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bookmark {
    private String title;
    private String url;
    private String description;
    private List<String> tags;

    public Bookmark(JSONObject object) throws JSONException {
        setTitle(object);
        setUrl(object);
        setDescription(object);
        setTags(object);
    }

    public void setTitle(JSONObject object) throws JSONException {
        title = object.getString("title");
    }

    public void setUrl(JSONObject object) throws JSONException {
        url = object.getString("url");
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

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        if (tags.isEmpty()) {
            return "";
        } else {
            String tagStr = tags.toString();
            return tagStr.substring(1, tagStr.length() - 1);
        }
    }
}
