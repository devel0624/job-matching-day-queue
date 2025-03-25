package com.nhnacademy.jobmatchingday.service;

import com.nhnacademy.jobmatchingday.domain.Company;
import com.nhnacademy.jobmatchingday.interview.queue.InterviewReservationQueue;
import com.nhnacademy.jobmatchingday.interview.stack.InterviewProcessStack;
import com.nhnacademy.jobmatchingday.repo.CompanyRepository;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final InterviewReservationQueue interviewReservationQueue;
    private final InterviewProcessStack interviewProcessStack;

    public CompanyService(CompanyRepository companyRepository, InterviewReservationQueue companyInterviewQueue,
                          InterviewProcessStack interviewProcessStack) {
        this.companyRepository = companyRepository;
        this.interviewReservationQueue = companyInterviewQueue;
        this.interviewProcessStack = interviewProcessStack;
    }

    public boolean registerCompany(String companyName) {
        Company company = new Company(null, companyName);

        company = companyRepository.save(company);
        interviewReservationQueue.addCompany(company);
        interviewProcessStack.addCompany(company);

        return Objects.nonNull(company.getId());
    }

    public boolean unregisterCompany(Long companyId) {
        if (companyRepository.remove(companyId)) {
            interviewReservationQueue.removeQueue(companyId);
            interviewProcessStack.removeStack(companyId);
            return true;
        }

        return false;
    }


    public Optional<Company> getCompany(Long key) {
        return companyRepository.getCompany(key);
    }

    public Collection<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
