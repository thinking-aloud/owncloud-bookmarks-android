package de.lkarsten.owncloudbookmarks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.lkarsten.owncloudbookmarks.view.BookmarkView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load BookmarkView
        Intent intent = new Intent(this, BookmarkView.class);
        startActivity(intent);
    }

}
