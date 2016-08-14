package lkarsten.de.owncloudbookmarks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lkarsten.de.owncloudbookmarks.util.Bookmark;

public class ListViewAdapter extends BaseAdapter {

    public List<Bookmark> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
//    TextView txtThird;
//    TextView txtFourth;

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

            txtFirst = (TextView) convertView.findViewById(R.id.title);
            txtSecond = (TextView) convertView.findViewById(R.id.url);
//            txtFourth = (TextView) convertView.findViewById(R.id.description);
//            txtThird = (TextView) convertView.findViewById(R.id.tags);
        }

        Bookmark map = list.get(position);

        txtFirst.setText(map.getTitle());
        txtSecond.setText(map.getUrl());
//        txtFourth.setText(map.getDescription());
//        txtThird.setText(map.getTags());

        return convertView;
    }

}
