package com.example.jcompia.tutoralnavi3.govweather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jun on 2017-11-26.
 */

public class WetherSpcnwsInfoServiceVO {
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
