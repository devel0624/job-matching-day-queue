package com.nhnacademy.jobmatchingday.controller;

import com.nhnacademy.jobmatchingday.domain.Company;
import com.nhnacademy.jobmatchingday.domain.Reservation;
import com.nhnacademy.jobmatchingday.domain.Student;
import com.nhnacademy.jobmatchingday.manager.InterviewBackUpManager;
import com.nhnacademy.jobmatchingday.service.CompanyService;
import com.nhnacademy.jobmatchingday.service.InterviewProcessService;
import com.nhnacademy.jobmatchingday.service.ReservationService;
import com.nhnacademy.jobmatchingday.service.StudentService;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Controller
public class IndexController {

    private final ReservationService reservationService;
    private final CompanyService companyService;
    private final StudentService studentService;
    private final InterviewProcessService interviewProcessService;
    private final InterviewBackUpManager interviewBackUpManager;

    public IndexController(ReservationService reservationService, CompanyService companyService,
                           StudentService studentService, InterviewProcessService interviewProcessService,
                           InterviewBackUpManager interviewBackUpManager) {
        this.reservationService = reservationService;
        this.companyService = companyService;
        this.studentService = studentService;
        this.interviewProcessService = interviewProcessService;
        this.interviewBackUpManager = interviewBackUpManager;
    }

    @GetMapping
    public String index(Model model) {
        Map<Long, List<Reservation>> reservations = reservationService.getReservations();
        Collection<Student> students = studentService.getAllStudents();
        Map<Long, List<Reservation>> inProgress = interviewProcessService.getInterviewProcessStack();
        Collection<Company> companies = companyService.getAllCompanies();

        model.addAttribute("students",
                students.stream().sorted(Comparator.comparing(Student::getTraineeNo)));

        model.addAttribute("companies", companies);
        model.addAttribute("interviewReservations", reservations);
        model.addAttribute("interviewInProgress", inProgress);

        interviewBackUpManager.backup();

        return "index";
    }

}
