package com.nhnacademy.jobmatchingday.domain;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class Company {

    private Long id;
    private String companyName;
    private int interviewCount;

    public Company(Long id, String companyName) {
        this.id = id;
        this.companyName = companyName;
        this.interviewCount = 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return companyName;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", interviewCount=" + interviewCount +
                '}';
    }

    public int getInterviewCount() {
        return interviewCount;
    }

    public void increaseInterviewCount() {
        interviewCount++;
    }

    public void setInterviewCount(Integer interviewCount) {
        this.interviewCount = interviewCount;
    }
}
