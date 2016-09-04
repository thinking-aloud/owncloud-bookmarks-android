package de.lkarsten.owncloudbookmarks.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.lkarsten.owncloudbookmarks.dto.Credentials;
import lkarsten.de.owncloudbookmarks.R;

public class SettingsActivity extends Activity {

    Credentials credentials;

    EditText etBaseUrl;
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        etBaseUrl = (EditText) findViewById(R.id.et_base_url);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        credentials = Credentials.getInstance();

        etBaseUrl.setText(credentials.getBaseUrl());
        etUsername.setText(credentials.getUsername());
        etPassword.setText(credentials.getPassword());
    }

    public void saveCredentials(View view) {
        String baseUrl = etBaseUrl.getText().toString().trim();
        if (baseUrl.length() > 0) {
            credentials.setBaseUrl(baseUrl);
        }

        String username = etUsername.getText().toString().trim();
        if (username.length() > 0) {
            credentials.setUsername(username);
        }

        String password = etPassword.getText().toString();
        if (password.length() > 0) {
            credentials.setPassword(password);
        }

        String text = "Credentials saved!";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}