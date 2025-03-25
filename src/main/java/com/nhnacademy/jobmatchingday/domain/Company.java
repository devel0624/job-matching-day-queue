package com.nhnacademy.jobmatchingday.domain;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class Company {

    private Long id;
    private String companyName;

    public Company(Long id, String companyName) {
        this.id = id;
        this.companyName = companyName;
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
                '}';
    }
}
