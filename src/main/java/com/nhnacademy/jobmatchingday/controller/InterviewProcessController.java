package com.nhnacademy.jobmatchingday.controller;

import com.nhnacademy.jobmatchingday.service.InterviewProcessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Controller
@RequestMapping("/interview/process")
public class InterviewProcessController {

    private final InterviewProcessService interviewProcessService;

    public InterviewProcessController(InterviewProcessService interviewProcessService) {
        this.interviewProcessService = interviewProcessService;
    }

    @PostMapping("/start")
    public String startInterview(@RequestParam Long companyNo) {
        interviewProcessService.interviewStark(companyNo);
        return "redirect:/";
    }

    @PostMapping("/finish")
    public String finishInterview(@RequestParam Long companyNo) {
        interviewProcessService.finishInterview(companyNo);
        return "redirect:/";
    }

    @PostMapping("/rollback")
    public String rollbackInterview(@RequestParam Long companyNo) {
        interviewProcessService.rollback(companyNo);
        return "redirect:/";
    }
}
