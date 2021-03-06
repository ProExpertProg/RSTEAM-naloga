package com.govedic.luka.rsteam;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dhavalnagar on 03/02/15.
 */
public class DownloadFileAsync extends AsyncTask<String, String, String> {

    private static final String TAG = "DOWNLOADFILE";

    private PostDownload callback;
    private File downloadLocation;

    DownloadFileAsync(File downloadLocation, PostDownload callback) {
        this.callback = callback;
        this.downloadLocation = downloadLocation;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;

        try {
            URL url = new URL(aurl[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lenghtOfFile = connection.getContentLength();
            Log.d(TAG, "Length of the file: " + lenghtOfFile);

            InputStream input = new BufferedInputStream(url.openStream());
            FileOutputStream output = new FileOutputStream(downloadLocation); //context.openFileOutput("content.zip", Context.MODE_PRIVATE);
            Log.d(TAG, "file saved at " + downloadLocation.getAbsolutePath());
            //FileDescriptor fd = output.getFD();

            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    protected void onProgressUpdate(String... progress) {
        Log.d(TAG,progress[0]);
    }

    @Override
    protected void onPostExecute(String unused) {
        if (callback != null) callback.downloadDone(downloadLocation);
    }

    public interface PostDownload {
        void downloadDone(File fd);
    }
}
