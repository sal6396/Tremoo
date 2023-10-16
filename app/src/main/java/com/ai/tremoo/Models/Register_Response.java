package com.ai.tremoo.Models;

import com.google.gson.annotations.SerializedName;

public class Register_Response {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public class Data {
        @SerializedName("user")
        private User user;

        @SerializedName("token")
        private String token;

        public User getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }
    }

    public class User {
        @SerializedName("id")
        private long id;

        @SerializedName("user_id")
        private long userId;

        @SerializedName("plan_id")
        private Long planId;

        @SerializedName("avatar")
        private String avatar;

        @SerializedName("storage")
        private String storage;

        @SerializedName("name")
        private String name;

        @SerializedName("number")
        private String number;

        @SerializedName("country")
        private String country;

        @SerializedName("city")
        private String city;

        @SerializedName("education")
        private String education;

        @SerializedName("is_smartphone")
        private int isSmartphone;
        @SerializedName("is_eligible")
        private int isEligible;

        public long getId() {
            return id;
        }

        public long getUserId() {
            return userId;
        }

        public Long getPlanId() {
            return planId;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getStorage() {
            return storage;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public String getEducation() {
            return education;
        }

        public int getIsSmartphone() {
            return isSmartphone;
        }

        public int getIsEligible() {
            return isEligible;
        }
    }
}
