package de.lkarsten.owncloudbookmarks.rest;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.lkarsten.owncloudbookmarks.util.AsyncResponse;

public class FaviconDownloader extends AsyncTask<URL, Void, Bitmap> {
    private AsyncResponse delegate = null;

    public FaviconDownloader(AsyncResponse asyncResponse) {
        this.delegate = asyncResponse;
    }

    @Override
    protected Bitmap doInBackground(URL... params) {
        try {
            return downloadFavicon(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        delegate.processFinish(result);
    }

    private Bitmap downloadFavicon(URL url) throws IOException {
        InputStream inputStream = null;
        url = new URL(url.getProtocol() + "://" + url.getHost() + "/favicon.ico");
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            if (connection.getResponseCode() != 200) {
                return null;
            }
            inputStream = connection.getInputStream();

            return BitmapFactory.decodeStream(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
