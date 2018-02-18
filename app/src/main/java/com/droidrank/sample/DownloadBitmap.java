package com.droidrank.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by wynk on 2/17/2018.
 */

public class DownloadBitmap extends AsyncTask<String, Integer, String> {

    private final String LOG_TAG = AsyncTask.class.getSimpleName();
    private Activity callingActivity;
    private DownloadImageCompletionListener downloadImageCompletionListener;
    private String imageWebURL;
    private int widthPixels, heightPixels;

    DownloadBitmap(Activity activity, DownloadImageCompletionListener downloadImageCompletionListener) {
        callingActivity = activity;
        this.downloadImageCompletionListener = downloadImageCompletionListener;

        DisplayMetrics metrics = new DisplayMetrics();
        callingActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d(LOG_TAG, "Display width in px is " + metrics.widthPixels);
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (Utility.isNetworkAvailable(callingActivity)) {
            Utility.showToastMessage(callingActivity, callingActivity.getString(R.string.PleaseConnectToInternet));
        } else
            Utility.showToastMessage(callingActivity, callingActivity.getString(R.string.Loading));
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(LOG_TAG, "doInBackground()");

        imageWebURL = params[0];

        Bitmap bitmap;
        if (Utility.isNetworkAvailable(callingActivity)) {
            bitmap = Utility.getBitmapFromURL(imageWebURL);

            Log.d(LOG_TAG, "resizing the actual bitmap: " + bitmap.getHeight() + bitmap.getWidth() + " as per screen size");

//            bitmap = Utility.getResizedBitmap(bitmap, heightPixels, widthPixels);

            return Utility.saveImageBitmapToFile(bitmap);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String downloadedLocalImageFilePath) {
        super.onPostExecute(downloadedLocalImageFilePath);
        Utility.showToastMessage(callingActivity, callingActivity.getString(R.string.FinishedLoading));

        downloadImageCompletionListener.onDownloadImageCompleted(downloadedLocalImageFilePath, imageWebURL);
    }

}