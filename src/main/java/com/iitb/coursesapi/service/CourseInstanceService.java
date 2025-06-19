package com.iitb.coursesapi.service;

import com.iitb.coursesapi.dto.CourseInstanceRequest;
import com.iitb.coursesapi.model.Course;
import com.iitb.coursesapi.model.CourseInstance;
import com.iitb.coursesapi.repository.CourseInstanceRepository;
import com.iitb.coursesapi.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CourseInstanceService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseInstanceRepository courseInstanceRepository;

    public CourseInstance createInstance(CourseInstanceRequest request) {
        Course course = courseRepository.findByCourseId(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Course not found: " + request.getCourseId()));

        CourseInstance instance = new CourseInstance();
        instance.setYear(request.getYear());
        instance.setSemester(request.getSemester());
        instance.setCourse(course);

        return courseInstanceRepository.save(instance);
    }

    public List<CourseInstance> getInstancesByYearSemester(int year, int semester) {
        return courseInstanceRepository.findByYearAndSemester(year, semester);
    }

    public CourseInstance getInstanceDetail(int year, int semester, String courseId) {
        return courseInstanceRepository.findByYearAndSemesterAndCourse_CourseId(year, semester, courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Instance not found for courseId: " + courseId + ", year: " + year + ", semester: " + semester));
    }

    public void deleteInstance(int year, int semester, String courseId) {
        CourseInstance instance = courseInstanceRepository.findByYearAndSemesterAndCourse_CourseId(year, semester, courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Instance not found to delete."));

        courseInstanceRepository.delete(instance);
    }
}
