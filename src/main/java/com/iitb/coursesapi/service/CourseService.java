package com.iitb.coursesapi.service;

import com.iitb.coursesapi.dto.CourseRequest;
import com.iitb.coursesapi.model.Course;
import com.iitb.coursesapi.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(CourseRequest request) {
        if (courseRepository.existsByCourseId(request.getCourseId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course ID already exists: " + request.getCourseId());
        }

        List<Course> prerequisites = new ArrayList<>();

        for (String prereqId : request.getPrerequisiteCourseIds()) {
            Course prereqCourse = courseRepository.findByCourseId(prereqId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Invalid prerequisite course ID: " + prereqId
                    ));
            prerequisites.add(prereqCourse);
        }

        Course course = new Course();
        course.setCourseId(request.getCourseId());
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrerequisites(prerequisites);

        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseByCourseId(String courseId) {
        return courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found: " + courseId));
    }

    public void deleteCourse(String courseId) {
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        boolean isUsedAsPrerequisite = courseRepository.findAll().stream()
                .anyMatch(c -> c.getPrerequisites().contains(course));

        if (isUsedAsPrerequisite) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete course. It is a prerequisite for other courses.");
        }

        courseRepository.delete(course);
    }
}
