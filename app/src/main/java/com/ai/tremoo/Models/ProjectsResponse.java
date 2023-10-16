package com.ai.tremoo.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProjectsResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Project> projects;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public String getMessage() {
        return message;
    }

    public static class Project {

        @SerializedName("id")
        private int id;

        @SerializedName("puid")
        private String puid;

        @SerializedName("category_id")
        private int categoryId;

        @SerializedName("title")
        private String title;

        @SerializedName("details")
        private String details;

        @SerializedName("country")
        private String country;

        @SerializedName("type")
        private String type;

        @SerializedName("status")
        private int status;

        @SerializedName("expiry_date")
        private String expiryDate;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("category")
        private Category category;

        public int getId() {
            return id;
        }

        public String getPuid() {
            return puid;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public String getTitle() {
            return title;
        }

        public String getDetails() {
            return details;
        }

        public String getCountry() {
            return country;
        }

        public String getType() {
            return type;
        }

        public int getStatus() {
            return status;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public Category getCategory() {
            return category;
        }
    }

    public static class Category {

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("slug")
        private String slug;

        @SerializedName("status")
        private int status;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSlug() {
            return slug;
        }

        public int getStatus() {
            return status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
