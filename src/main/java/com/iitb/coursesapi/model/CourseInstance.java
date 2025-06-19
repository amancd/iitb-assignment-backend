package com.iitb.coursesapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CourseInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int semester;

    @ManyToOne
    private Course course;

    // Getters and setters
}
