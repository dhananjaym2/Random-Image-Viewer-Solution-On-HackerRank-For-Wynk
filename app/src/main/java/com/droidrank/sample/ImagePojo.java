package com.droidrank.sample;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wynk on 2/17/2018.
 */

public class ImagePojo {
    private String imageUrl;
    private String imageDescription;
    private String localImagePath;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
