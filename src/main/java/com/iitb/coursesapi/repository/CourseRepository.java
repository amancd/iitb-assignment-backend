package com.iitb.coursesapi.repository;

import com.iitb.coursesapi.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
    // Find course by its unique courseId (e.g., CS209)
    Optional<Course> findByCourseId(String courseId);
    
    // Check if courseId already exists (to avoid duplicates)
    boolean existsByCourseId(String courseId);
}
