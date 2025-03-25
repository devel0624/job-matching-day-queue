package com.nhnacademy.jobmatchingday.controller;

import com.nhnacademy.jobmatchingday.service.ReservationService;
import com.nhnacademy.jobmatchingday.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : 이성준
 * @since : 1.0i
 */

@Controller
@RequestMapping("/reservation")
public class InterviewReservationController {

    private final ReservationService reservationService;
    private final StudentService studentService;

    public InterviewReservationController(ReservationService reservationService, StudentService studentService) {
        this.reservationService = reservationService;
        this.studentService = studentService;
    }

    @PostMapping
    public String reserveInterview(@RequestParam Long companyNo, @RequestParam String studentName) {
        studentService.getStudent(studentName)
                .ifPresent(student -> reservationService.registerReservation(companyNo, student));

        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteReservation(@RequestParam Long companyNo, @RequestParam Long order) {
        reservationService.deleteReservation(companyNo, order);
        return "redirect:/";
    }
}
