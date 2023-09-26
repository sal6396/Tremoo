package com.ai.tremoo.Models;

public class ProjectModel {

    private String projectId;
    private int id;
    private String projectName;
    private String projectDescription;
    private String projectStatus;
    private String projectCategory;
    private String projectExpiry;

    public ProjectModel(String id, String projectId, String projectName, String projectDescription, String projectStatus, String projectCategory, String projectExpiry) {
        this.id = Integer.parseInt(id);
        this.projectId = projectId;
        this.projectDescription = projectDescription;
        this.projectName = projectName;
        this.projectStatus = projectStatus;
        this.projectCategory = projectCategory;
        this.projectExpiry = projectExpiry;
    }

    public int getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }
    public String getProjectName() {
        return projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public String getProjectExpiry() {
        return projectExpiry;
    }
}

