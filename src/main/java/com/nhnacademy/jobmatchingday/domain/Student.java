package com.nhnacademy.jobmatchingday.domain;

import java.util.Objects;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class Student {
    @Override
    public String toString() {
        return "Student{" +
                "traineeNo='" + traineeNo + '\'' +
                ", name='" + name + '\'' +
                ", interviewCount=" + interviewCount +
                '}';
    }

    private String traineeNo;
    private String name;
    private int interviewCount;

    public Student(String traineeNo, String name) {
        this(traineeNo, name, 0);
    }

    public Student(String traineeNo, String name, int interviewCount) {
        this.traineeNo = traineeNo;
        this.name = name;
        this.interviewCount = interviewCount;
    }

    public String getName() {
        return name;
    }

    public String getTraineeNo() {
        return traineeNo;
    }

    public int getInterviewCount() {
        return interviewCount;
    }

    public void incrementInterviewCount() {
        interviewCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return interviewCount == student.interviewCount && Objects.equals(name, student.name) &&
                Objects.equals(traineeNo, student.traineeNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, traineeNo, interviewCount);
    }
}
