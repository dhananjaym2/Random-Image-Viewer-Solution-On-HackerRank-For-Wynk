package com.droidrank.sample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wynk on 2/17/2018.
 */

public class JSONParser {
    private final String TAG = JSONParser.class.getSimpleName();

    ArrayList<ImagePojo> parseJSONResponse_fetchImagesData(String stringOfJSON) throws JSONException {
        ArrayList<ImagePojo> image_Pojo_ArrayList = null;
        JSONObject parent_jsonObject;
        parent_jsonObject = new JSONObject(stringOfJSON);
        if (parent_jsonObject.has(JSONResponseTags.success)) {

            if (parent_jsonObject.getString(JSONResponseTags.success).equals(JSONResponseTags.success_1)) {
                if (parent_jsonObject.has(JSONResponseTags.images)) {
                    JSONArray images_jsonArray = parent_jsonObject.getJSONArray(JSONResponseTags.images);
                    if (images_jsonArray != null) {

                        for (int index = 0; index < images_jsonArray.length(); index++) {
                            JSONObject image_jsonObject = images_jsonArray.getJSONObject(index);
                            if (image_jsonObject != null) {
                                ImagePojo imagePojo = new ImagePojo();
                                if (image_jsonObject.has(JSONResponseTags.imageUrl)) {
                                    imagePojo.setImageUrl(image_jsonObject.getString(JSONResponseTags.imageUrl));
                                } else
                                    throw new JSONException("JSON Response does not have element: " + JSONResponseTags.imageUrl);

                                if (image_jsonObject.has(JSONResponseTags.imageDescription)) {
                                    imagePojo.setImageDescription(image_jsonObject.getString(JSONResponseTags.imageDescription));
                                } else
                                    throw new JSONException("JSON Response does not have element: " + JSONResponseTags.imageDescription);

                                if (image_Pojo_ArrayList == null) {
                                    image_Pojo_ArrayList = new ArrayList<>();
                                }
                                image_Pojo_ArrayList.add(imagePojo);
                            } else
                                throw new JSONException("JSON Response does not have inner element: " + JSONResponseTags.images + "as a JSON Object");
                        }
                    } else
                        throw new JSONException("JSON Response does not have element: " + JSONResponseTags.images + "as a JSON array");
                } else
                    throw new JSONException("JSON Response does not have element: " + JSONResponseTags.images);
            } else
                throw new JSONException("JSON Response element: " + JSONResponseTags.success + " value is not:" + JSONResponseTags.success_1);
        } else
            throw new JSONException("JSON Response does not have element: " + JSONResponseTags.success);
        return image_Pojo_ArrayList;
    }
}
