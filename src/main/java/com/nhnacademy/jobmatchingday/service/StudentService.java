package com.nhnacademy.jobmatchingday.service;

import com.nhnacademy.jobmatchingday.domain.Student;
import com.nhnacademy.jobmatchingday.manager.IOManager;
import com.nhnacademy.jobmatchingday.repo.StudentRepository;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Component
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void registerStudent(String traineeNo, String traineeName) {
        studentRepository.save(new Student(traineeNo, traineeName));

    }

    public Optional<Student> getStudent(String traineeName) {
        return studentRepository.findByTraineeName(traineeName);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
