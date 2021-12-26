package com.nurali.kampunggerabah.api.responses;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;
}
