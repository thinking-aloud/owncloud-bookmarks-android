package de.lkarsten.owncloudbookmarks.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.lkarsten.owncloudbookmarks.dto.Bookmark;
import lkarsten.de.owncloudbookmarks.R;

public class BookmarkAdapter extends ArrayAdapter<Bookmark> {

    public BookmarkAdapter(Context context, ArrayList<Bookmark> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bookmark bookmark = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_bookmark_row, parent, false);
        }

        // Lookup view for data population
        ImageView imgFavicon = (ImageView) convertView.findViewById(R.id.favicon);
        TextView tvHome = (TextView) convertView.findViewById(R.id.title);

        // Populate the data into the template view using the data object
        if (bookmark.getFavicon() != null) {
            imgFavicon.setImageBitmap(bookmark.getFavicon());
            imgFavicon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        tvHome.setText(bookmark.getTitle());

        // Return the completed view to render on screen
        return convertView;
    }
}