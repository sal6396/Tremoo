package com.ai.tremoo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SubmissionData {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Map<String, String[]> errorData;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String[]> getErrorData() {
        return errorData;
    }
}
