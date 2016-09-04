package de.lkarsten.owncloudbookmarks.dto;


import android.content.SharedPreferences;

import de.lkarsten.owncloudbookmarks.MainActivity;

public class Credentials {
    private static Credentials instance = null;

    private static final String PREFS_NAME = "ownCloudBookmarkPrefs";

    private static String baseUrl;
    private static String username;
    private static String password;

    private static SharedPreferences settings;


    private Credentials() {
    }

    public static Credentials getInstance() {
        if (instance == null) {
            instance = new Credentials();
            settings = MainActivity.getInstance().getSharedPreferences(PREFS_NAME, 0);

            baseUrl = settings.getString("baseUrl", "");
            username = settings.getString("username", "");
            password = settings.getString("password", "");
        }
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        Credentials.baseUrl = baseUrl;
        updateSettings("baseUrl", baseUrl);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Credentials.username = username;
        updateSettings("username", username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Credentials.password = password;
        updateSettings("password", password);
    }

    private void updateSettings(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
