package de.lkarsten.owncloudbookmarks.rest;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookmarkDownloader extends AsyncTask<URL, Void, String> {

    @Override
    protected String doInBackground(URL... params) {

        try {
            return downloadBookmarks(params[0]);
        } catch (IOException e) {
            return "Unable to retrieve data. URL may be invalid.";
        }

    }

    private String downloadBookmarks(URL url) throws IOException {
        InputStream inputStream = null;

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            inputStream = connection.getInputStream();

            return convertInputStreamToString(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder(inputStream.available());
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

}
