package com.iitb.coursesapi.repository;

import com.iitb.coursesapi.model.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseInstanceRepository extends JpaRepository<CourseInstance, Long> {

    // Get all instances for a specific year and semester
    List<CourseInstance> findByYearAndSemester(int year, int semester);

    // Get a specific instance by year, semester, and courseId
    Optional<CourseInstance> findByYearAndSemesterAndCourse_CourseId(int year, int semester, String courseId);
}
