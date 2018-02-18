package com.droidrank.sample;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by wynk on 2/17/2018.
 */

public class HTTPGetRequest extends AsyncTask<String, Integer, ArrayList<ImagePojo>> {

    private final String LOG_TAG = AsyncTask.class.getSimpleName();
    private final Activity callingActivity;
    private FetchImagesDataAPICompletionListener fetchImagesDataAPICompletionListener;

    HTTPGetRequest(Activity appCompatActivity, FetchImagesDataAPICompletionListener fetchImagesDataAPICompletionListener) {
        callingActivity = appCompatActivity;
        this.fetchImagesDataAPICompletionListener = fetchImagesDataAPICompletionListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Utility.showToastMessage(callingActivity, callingActivity.getString(R.string.Loading));
    }

    @Override
    protected ArrayList<ImagePojo> doInBackground(String... params) {
        Log.d(LOG_TAG, "doInBackground()");
        String urlToLoadDataFrom = params[0];

        try {
            URL url = new URL(urlToLoadDataFrom);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // setting the  Request Method Type
            httpURLConnection.setRequestMethod("GET");
            // adding the headers for request
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            try {
                //to tell the connection object that we will be wrting some data on the server and then will fetch the output result
                httpURLConnection.setDoOutput(true);
                // this is used for just in case we don't know about the data size associated with our request
                httpURLConnection.setChunkedStreamingMode(0);

                // to write tha data in our request
                OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

                outputStreamWriter.flush();
                outputStreamWriter.close();

                // to log the response code of your request
                Log.d(LOG_TAG, "responseCode: " + httpURLConnection.getResponseCode());
                // to log the response message from your server after you have tried the request.
                Log.d(LOG_TAG, "responseMessage: " + httpURLConnection.getResponseMessage());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Log.d(LOG_TAG, "Response: " + response.toString());

                return new JSONParser().parseJSONResponse_fetchImagesData(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // this is done so that there are no open connections left when this task is going to complete
                httpURLConnection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<ImagePojo> imagePojoArrayList) {
        super.onPostExecute(imagePojoArrayList);
        Utility.showToastMessage(callingActivity, callingActivity.getString(R.string.FinishedLoading));

        fetchImagesDataAPICompletionListener.onFetchImagesDataAPICompleted(imagePojoArrayList);
    }
}