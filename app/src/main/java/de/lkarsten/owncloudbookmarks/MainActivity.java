package de.lkarsten.owncloudbookmarks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.lkarsten.owncloudbookmarks.activity.BookmarkActivity;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        Intent intent = new Intent(this, BookmarkActivity.class);
        startActivity(intent);
    }

    public static MainActivity getInstance() {
        return instance;
    }

}
