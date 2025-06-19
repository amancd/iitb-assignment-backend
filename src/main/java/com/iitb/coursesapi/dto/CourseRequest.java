package com.iitb.coursesapi.dto;

import java.util.List;

public class CourseRequest {
    private String courseId;
    private String title;
    private String description;
    private List<String> prerequisiteCourseIds;

    // ✅ Getters
    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getPrerequisiteCourseIds() {
        return prerequisiteCourseIds;
    }

    // ✅ Setters
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrerequisiteCourseIds(List<String> prerequisiteCourseIds) {
        this.prerequisiteCourseIds = prerequisiteCourseIds;
    }
}
