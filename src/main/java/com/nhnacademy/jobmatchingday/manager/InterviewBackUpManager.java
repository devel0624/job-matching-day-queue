package com.nhnacademy.jobmatchingday.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.jobmatchingday.domain.Company;
import com.nhnacademy.jobmatchingday.domain.Reservation;
import com.nhnacademy.jobmatchingday.domain.Student;
import com.nhnacademy.jobmatchingday.interview.queue.InterviewReservationQueue;
import com.nhnacademy.jobmatchingday.interview.stack.InterviewProcessStack;
import com.nhnacademy.jobmatchingday.repo.CompanyRepository;
import com.nhnacademy.jobmatchingday.repo.StudentRepository;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.springframework.stereotype.Component;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Component
public class InterviewBackUpManager {

    private final ObjectMapper objectMapper;
    private final InterviewReservationQueue interviewReservationQueue;
    private final InterviewProcessStack interviewProcessStack;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public InterviewBackUpManager(ObjectMapper objectMapper, InterviewReservationQueue interviewReservationQueue,
                                  InterviewProcessStack interviewProcessStack, StudentRepository studentRepository,
                                  CompanyRepository companyRepository) {
        this.objectMapper = objectMapper;
        this.interviewReservationQueue = interviewReservationQueue;
        this.interviewProcessStack = interviewProcessStack;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    public void backup() {
        Map<Long, Stack<Reservation>> inProgress = interviewProcessStack.getInterviewProcessStack();
        Map<Long, List<Reservation>> reservations = interviewReservationQueue.getReservations();
        Collection<Student> students = studentRepository.findAll();
        Collection<Company> companies = companyRepository.findAll();

        // 데이터를 포함할 객체 생성
        Map<String, Object> backupData = Map.of(
                "students", students,
                "companies", companies,
                "inProgress", inProgress,
                "reservations", reservations
        );

        // 파일로 저장
        try {
            objectMapper.writeValue(new File("backup.json"), backupData);
            System.out.println("Backup saved successfully.");
        } catch (IOException e) {
            System.err.println("Error while saving backup: " + e.getMessage());
        }

    }

    public void loadBackup() {
        // ObjectMapper 생성

        try {
            // JSON 파일을 Map으로 읽기
            File backupFile = new File("backup.json");
            Map<String, Object> backupData =
                    objectMapper.readValue(backupFile, new TypeReference<Map<String, Object>>() {
                    });

            backupCompanies((List<Map<String, Object>>) backupData.get("companies"));
            backupStudents((List<Map<String, Object>>) backupData.get("students"));
            backupInProgress((Map<String, Object>) backupData.get("inProgress"));
            backupReservation((Map<String, Object>) backupData.get("reservations"));


            System.out.println("Backup loaded successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Error while loading backup: " + e.getMessage());
        }
    }

    private void backupStudents(List<Map<String, Object>> students) {

        students.forEach(
                map -> {
                    String traineeNo = (String) map.get("traineeNo");
                    String name = (String) map.get("name");
                    int interviewCount = (Integer) map.get("interviewCount");

                    Student student = new Student(traineeNo, name, interviewCount);
                    studentRepository.save(student);
                }
        );

    }


    private void backupCompanies(List<Map<String, Object>> companies) {

        companies.forEach(
                map -> {
                    Integer companyNo = (Integer) map.get("id");
                    String name = (String) map.get("name");

                    Company company = new Company(companyNo.longValue(), name);
                    companyRepository.save(company);
                }
        );

    }

    private void backupReservation(Map<String, Object> backupData) {
        Map<Long, LinkedList<Reservation>> queues = new HashMap<>(backupData.size());

        backupData.forEach((key, value) -> {
            List<Object> reservations = (List<Object>) value;
            Long companyNo = Long.valueOf(key);
            LinkedList<Reservation> reservationQueue = new LinkedList<>();

            reservations.forEach(
                    r -> {

                        Map<String, Object> reservationMap = (Map<String, Object>) r;

                        Map<String, Object> studentMap =
                                (Map<String, Object>) reservationMap.get("student");
                        int order = Integer.parseInt(reservationMap.get("order").toString());

                        String traineeNo = (String) studentMap.get("traineeNo");
                        String name = (String) studentMap.get("name");
                        int interviewCount = (Integer) studentMap.get("interviewCount");

                        Student student = new Student(traineeNo, name, interviewCount);

                        Reservation reservation = new Reservation(student, order);

                        reservationQueue.add(reservation);

                    }
            );

            queues.put(companyNo, reservationQueue);
        });

        // inProgress와 reservations을 원래 타입에 맞게 변환
        interviewReservationQueue.setReservations(queues);
    }

    private void backupInProgress(Map<String, Object> backupData) {
        Map<Long, Stack<Reservation>> inProgress = new HashMap<>(backupData.size());

        backupData.forEach((key, value) -> {
            List<Object> reservations = (List<Object>) value;
            Long companyNo = Long.valueOf(key);
            Stack<Reservation> progressStack = new Stack<>();

            reservations.forEach(
                    r -> {

                        Map<String, Object> reservationMap = (Map<String, Object>) r;

                        Map<String, Object> studentMap =
                                (Map<String, Object>) reservationMap.get("student");
                        int order = Integer.parseInt(reservationMap.get("order").toString());

                        String traineeNo = (String) studentMap.get("traineeNo");
                        String name = (String) studentMap.get("name");
                        int interviewCount = (Integer) studentMap.get("interviewCount");

                        Student student = new Student(traineeNo, name, interviewCount);

                        Reservation reservation = new Reservation(student, order);

                        progressStack.add(reservation);

                    }
            );

            inProgress.put(companyNo, progressStack);
        });

        interviewProcessStack.setInterviewProcessStack(inProgress);
    }

}
