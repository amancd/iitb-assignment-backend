package com.iitb.coursesapi.dto;
import lombok.Data;

@Data
public class CourseInstanceRequest {
    private String courseId;
    private int year;
    private int semester;

    // Getters and setters
}
