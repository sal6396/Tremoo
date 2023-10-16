package com.ai.tremoo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectsResponse {

    @SerializedName("data")
    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public static class Project {
        @SerializedName("id")
        private int id;

        @SerializedName("puid")
        private String puid;

        public String getDetails() {
            return details;
        }

        @SerializedName("details")
        private String details;

        @SerializedName("status")
        private int status;

        @SerializedName("expiry_date")
        private String expiryDate;

        @SerializedName("title")
        private String title;

        @SerializedName("category")
        private Category category;

        public String getPuid() {
            return puid;
        }

        public String getTitle() {
            return title;
        }

        public int getStatus() {
            return status;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public Category getCategory() {
            return category;
        }

        // Additional fields and methods can be added here based on your requirements
    }

    public static class Category {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }

        // Additional fields and methods can be added here based on your requirements
    }
}
