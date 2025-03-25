package com.nhnacademy.jobmatchingday.repo;

import com.nhnacademy.jobmatchingday.domain.Student;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Repository
public class StudentRepository {

    Map<String, Student> students;

    public StudentRepository() {
        students = new HashMap<>();
    }

    public Student save(Student student) {
        students.put(student.getTraineeNo(), student);
        return student;
    }

    public Optional<Student> findByTraineeName(String traineeName) {
        for (Student student : students.values()) {
            if (student.getName().equals(traineeName)) {
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public Collection<Student> findAll() {
        return students.values();
    }
}
