package com.iitb.coursesapi.controller;

import com.iitb.coursesapi.dto.CourseRequest;
import com.iitb.coursesapi.model.Course;
import com.iitb.coursesapi.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    // POST /api/courses - Create a new course
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseRequest request) {
        List<Course> prerequisites = new ArrayList<>();

        // Validate prerequisites
        for (String prereqId : request.getPrerequisiteCourseIds()) {
            Optional<Course> prereq = courseRepository.findByCourseId(prereqId);
            if (prereq.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid prerequisite course ID: " + prereqId);
            }
            prerequisites.add(prereq.get());
        }

        // Check for duplicate courseId
        if (courseRepository.existsByCourseId(request.getCourseId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Course ID already exists: " + request.getCourseId());
        }

        // Create course
        Course course = new Course();
        course.setCourseId(request.getCourseId());
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrerequisites(prerequisites);

        courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    // GET /api/courses - List all courses with prerequisites
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    // GET /api/courses/{id} - Get one course by CourseID (e.g., CS209)
@GetMapping("/{id}")
public ResponseEntity<?> getCourseById(@PathVariable String id) {
    Optional<Course> courseOpt = courseRepository.findByCourseId(id);
    
    if (courseOpt.isPresent()) {
        return ResponseEntity.ok(courseOpt.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Course not found with ID: " + id);
    }
}


    // DELETE /api/courses/{id} - Delete course with validation
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        Optional<Course> courseOpt = courseRepository.findByCourseId(id);

        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course not found with ID: " + id);
        }

        Course course = courseOpt.get();

        // Check if this course is used as a prerequisite
        boolean isPrerequisite = courseRepository.findAll().stream()
                .anyMatch(c -> c.getPrerequisites().contains(course));

        if (isPrerequisite) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Cannot delete course. It is a prerequisite for other courses.");
        }

        courseRepository.delete(course);
        return ResponseEntity.ok("Course deleted successfully.");
    }
}
