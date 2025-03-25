package com.nhnacademy.jobmatchingday.controller;

import com.nhnacademy.jobmatchingday.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public String addStudent(@RequestParam String traineeName, @RequestParam String traineeNo) {
        studentService.registerStudent(traineeNo, traineeName);
        return "redirect:/";
    }


}
