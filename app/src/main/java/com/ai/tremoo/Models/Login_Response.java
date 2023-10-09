package com.ai.tremoo.Models;

import com.google.gson.annotations.SerializedName;

public class Login_Response {
    private boolean success;
    private Data data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {
        private User user;

        @SerializedName("token")
        private String token;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public class User {
        private long id;

        @SerializedName("email")
        private String email;

        @SerializedName("is_email_verified")
        private long isEmailVerified;

        @SerializedName("profile")
        private Profile profile;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public long getIsEmailVerified() {
            return isEmailVerified;
        }

        public void setIsEmailVerified(long isEmailVerified) {
            this.isEmailVerified = isEmailVerified;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }
    }

    public class Profile {
        private long id;
        private long userId;
        private Long planId;
        private String avatar;
        private String storage;
        private String name;
        private String number;
        private String gender;
        private String country;
        private String city;
        private String education;
        private long isSmartphone;
        private long isEligible;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public Long getPlanId() {
            return planId;
        }

        public void setPlanId(Long planId) {
            this.planId = planId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getStorage() {
            return storage;
        }

        public void setStorage(String storage) {
            this.storage = storage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public long getIsSmartphone() {
            return isSmartphone;
        }

        public void setIsSmartphone(long isSmartphone) {
            this.isSmartphone = isSmartphone;
        }

        public long getIsEligible() {
            return isEligible;
        }

        public void setIsEligible(long isEligible) {
            this.isEligible = isEligible;
        }
    }
}
