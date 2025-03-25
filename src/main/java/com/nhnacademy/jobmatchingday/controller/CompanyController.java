package com.nhnacademy.jobmatchingday.controller;

import com.nhnacademy.jobmatchingday.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping
    public String addCompany(@RequestParam String name) {
        companyService.registerCompany(name);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteCompany(@RequestParam Long companyNo) {
        companyService.unregisterCompany(companyNo);
        return "redirect:/";
    }

}
