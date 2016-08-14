package de.lkarsten.owncloudbookmarks.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lkarsten.de.owncloudbookmarks.R;

public class ListViewAdapter extends BaseAdapter {

    public List<Bookmark> list;
    Activity activity;
    TextView txtTitle;
    ImageView imgFavicon;

    public ListViewAdapter(Activity activity, List<Bookmark> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.activity_bookmarkview_row, null);

            txtTitle = (TextView) convertView.findViewById(R.id.title);
            imgFavicon = (ImageView) convertView.findViewById(R.id.favicon);
        }

        Bookmark map = list.get(position);

        txtTitle.setText(map.getTitle());

        if (map.getFavicon() != null) {
            imgFavicon.setImageBitmap(map.getFavicon());
            imgFavicon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        return convertView;
    }

}
