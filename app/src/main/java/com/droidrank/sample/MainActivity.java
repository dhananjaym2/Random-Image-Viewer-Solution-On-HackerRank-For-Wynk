package com.droidrank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FetchImagesDataAPICompletionListener, DownloadImageCompletionListener, View.OnClickListener {

    private Button previous, next;
    private int offsetValue = 0;
    private ImageView imageview;
    private int index = 0;
    private ArrayList<ImagePojo> image_Pojo_ArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        loadDataFromWebAPI(offsetValue);
    }

    private void loadDataFromWebAPI(int offsetValue) {
        HTTPGetRequest httpGetRequest = new HTTPGetRequest(MainActivity.this, MainActivity.this);

        Utility.execute(httpGetRequest, getString(R.string.fetchImagesDataURLPrefix, offsetValue));
    }

    private void initView() {

        previous = (Button) findViewById(R.id.previous);
        //onclick of previous button should navigate the user to previous image
        previous.setOnClickListener(this);

        next = (Button) findViewById(R.id.next);
        //onclick of next button should navigate the user to next image
        next.setOnClickListener(this);

        imageview = (ImageView) findViewById(R.id.imageview);
    }

    @Override
    public void onFetchImagesDataAPICompleted(ArrayList<ImagePojo> image_Pojo_ArrayList_new) {
        if (image_Pojo_ArrayList == null) {
            image_Pojo_ArrayList = new ArrayList<>();
        }
        this.image_Pojo_ArrayList.addAll(image_Pojo_ArrayList_new);
        showImage(index);
    }

    @Override
    public void onDownloadImageCompleted(String downloadedLocalImageFilePath, String imageWebURL) {

        Utility.setBitmapInImageViewFromLocalPath(downloadedLocalImageFilePath, imageview);

        for (int i = 0; i < image_Pojo_ArrayList.size(); i++) {
            if (image_Pojo_ArrayList.get(i).getImageUrl().equalsIgnoreCase(imageWebURL)) {
                image_Pojo_ArrayList.get(i).setLocalImagePath(downloadedLocalImageFilePath);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.next:
                index++;
                if (index < offsetValue) {
                    showImage(index);
                } else {
                    if (!Utility.isNetworkAvailable(MainActivity.this)) {
                        Utility.showToastMessage(MainActivity.this, getString(R.string.PleaseConnectToInternet));
                    } else {
                        offsetValue++;
                        loadDataFromWebAPI(offsetValue);
                    }
                }
                break;

            case R.id.previous:
                if (index == 0) {
                    break;
                } else {
                    index--;
                }

                if (!Utility.isNetworkAvailable(MainActivity.this)) {
                    Utility.showToastMessage(MainActivity.this, getString(R.string.PleaseConnectToInternet));
                } else {
                    offsetValue--;
                    loadDataFromWebAPI(offsetValue);
                }
                break;
        }
    }

    private void showImage(int index) {

        if (image_Pojo_ArrayList.get(index).getLocalImagePath() == null) {
            /*Download image and save it locally*/
            DownloadBitmap downloadBitmap = new DownloadBitmap(MainActivity.this,
                    MainActivity.this);
            Utility.execute(downloadBitmap, image_Pojo_ArrayList.get(index).getImageUrl());
        } else {
            Utility.setBitmapInImageViewFromLocalPath(image_Pojo_ArrayList.get(index).getLocalImagePath(), imageview);
        }
    }
}