package com.ai.tremoo.Models;

import com.google.gson.annotations.SerializedName;

public class Register_Response {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
