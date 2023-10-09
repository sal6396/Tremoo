package com.ai.tremoo;

public class RequestHandler {

    private static RequestHandler instance;
    private ApiService apiService;

    private RequestHandler() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    public static synchronized RequestHandler getInstance() {
        if (instance == null) {
            instance = new RequestHandler();
        }
        return instance;
    }

    public ApiService getApi() {
        return apiService;
    }
}
