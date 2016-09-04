package de.lkarsten.owncloudbookmarks.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import lkarsten.de.owncloudbookmarks.R;

public class SettingsActivity extends Activity {

    public static final String PREFS_NAME = "ownCloudBookmarkPrefs";

    EditText etServerUrl;
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        etServerUrl = (EditText) findViewById(R.id.et_server_url);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        String server_url = settings.getString("server_url", "");
        String username = settings.getString("username", "");
        String password = settings.getString("password", "");

        etServerUrl.setText(server_url);
        etUsername.setText(username);
        etPassword.setText(password);
    }

    public void saveCredentials(View view) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        String server_url = etServerUrl.getText().toString().trim();
        if (server_url.length() > 0) {
            editor.putString("server_url", server_url);
        }

        String username = etUsername.getText().toString().trim();
        if (username.length() > 0) {
            editor.putString("username", username);
        }

        String password = etPassword.getText().toString();
        if (password.length() > 0) {
            editor.putString("password", password);
        }

        // Commit the edits!
        editor.apply();

        String text = "Credentials saved!";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}