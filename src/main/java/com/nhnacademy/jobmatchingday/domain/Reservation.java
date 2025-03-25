package com.nhnacademy.jobmatchingday.domain;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class Reservation {

    private Student student;
    private int order;

    public Reservation(Student student, int order) {
        this.student = student;
        this.order = order;
    }

    public Student getStudent() {
        return student;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "student=" + student +
                ", order=" + order +
                '}';
    }
}
