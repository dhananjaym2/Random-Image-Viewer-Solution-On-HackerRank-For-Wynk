package com.droidrank.sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wynk on 2/17/2018.
 */
public class FetchImagesDataAPIResponse {

    private List<ImagePojo> imagePojos = null;
    private Integer success;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<ImagePojo> getImagePojos() {
        return imagePojos;
    }

    public void setImagePojos(List<ImagePojo> imagePojos) {
        this.imagePojos = imagePojos;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}