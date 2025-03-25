package com.nhnacademy.jobmatchingday.interview.stack;

import com.nhnacademy.jobmatchingday.domain.Company;
import com.nhnacademy.jobmatchingday.domain.Reservation;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.springframework.stereotype.Component;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Component
public class InterviewProcessStack {

    private Map<Long, Stack<Reservation>> interviewProcessStack;

    public InterviewProcessStack() {
        interviewProcessStack = new HashMap<>();
    }
    /**
     * 인터뷰 진행 공간 생성
     * @param company
     */
    public void addCompany(Company company) {
        interviewProcessStack.put(company.getId(), new Stack<>());
    }

    /**
     * 진행 추가
     * @param companyId
     * @param reservation
     */
    public void push(Long companyId, Reservation reservation) {
        interviewProcessStack.get(companyId).push(reservation);
    }

    /**
     * 가장 마지막에 추가된 진행 목록 제거.
     * @param companyId
     * @return
     */
    public Reservation pop(Long companyId) {
        return interviewProcessStack.get(companyId).pop();
    }

    /**
     * 진행 목록 전체 제거
     *
     * @param companyId
     * @return
     */
    public Stack<Reservation> getStack(Long companyId) {
        return interviewProcessStack.get(companyId);
    }

    public Map<Long, Stack<Reservation>> getInterviewProcessStack() {
        return interviewProcessStack;
    }

    public void removeStack(Long companyId) {
        interviewProcessStack.remove(companyId);
    }

    public void setInterviewProcessStack(Map<Long, Stack<Reservation>> inProgress) {
        interviewProcessStack = inProgress;
    }
}
