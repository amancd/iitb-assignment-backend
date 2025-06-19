package com.iitb.coursesapi.controller;

import com.iitb.coursesapi.dto.CourseInstanceRequest;
import com.iitb.coursesapi.model.Course;
import com.iitb.coursesapi.model.CourseInstance;
import com.iitb.coursesapi.repository.CourseInstanceRepository;
import com.iitb.coursesapi.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instances")
public class CourseInstanceController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    // POST /api/instances - Create a new instance
    @PostMapping
    public ResponseEntity<?> createInstance(@RequestBody CourseInstanceRequest request) {
        Optional<Course> courseOpt = courseRepository.findByCourseId(request.getCourseId());

        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Course ID not found: " + request.getCourseId());
        }

        CourseInstance instance = new CourseInstance();
        instance.setYear(request.getYear());
        instance.setSemester(request.getSemester());
        instance.setCourse(courseOpt.get());

        courseInstanceRepository.save(instance);
        return ResponseEntity.status(HttpStatus.CREATED).body(instance);
    }

    // GET /api/instances/{year}/{semester} - List instances
    @GetMapping("/{year}/{semester}")
    public ResponseEntity<List<CourseInstance>> getInstancesByYearSemester(
            @PathVariable int year,
            @PathVariable int semester) {

        List<CourseInstance> instances = courseInstanceRepository.findByYearAndSemester(year, semester);
        return ResponseEntity.ok(instances);
    }

    // GET /api/instances/{year}/{semester}/{courseId} - View specific instance
    @GetMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<?> getInstanceDetail(
            @PathVariable int year,
            @PathVariable int semester,
            @PathVariable String courseId) {

        Optional<CourseInstance> instanceOpt =
                courseInstanceRepository.findByYearAndSemesterAndCourse_CourseId(year, semester, courseId);

        if (instanceOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course instance not found for ID: " + courseId + ", Year: " + year + ", Semester: " + semester);
        }

        return ResponseEntity.ok(instanceOpt.get());
    }

    // DELETE /api/instances/{year}/{semester}/{courseId} - Delete an instance
    @DeleteMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<?> deleteInstance(
            @PathVariable int year,
            @PathVariable int semester,
            @PathVariable String courseId) {

        Optional<CourseInstance> instanceOpt =
                courseInstanceRepository.findByYearAndSemesterAndCourse_CourseId(year, semester, courseId);

        if (instanceOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course instance not found to delete.");
        }

        courseInstanceRepository.delete(instanceOpt.get());
        return ResponseEntity.ok("Course instance deleted.");
    }
}
