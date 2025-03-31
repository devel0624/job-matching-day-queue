package com.nhnacademy.jobmatchingday.interview.queue;

import com.nhnacademy.jobmatchingday.domain.Company;
import com.nhnacademy.jobmatchingday.domain.Reservation;
import com.nhnacademy.jobmatchingday.domain.Student;
import com.nhnacademy.jobmatchingday.repo.CompanyRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author : 이성준
 * @since : 1.0
 */


@Component
public class InterviewReservationQueue {

    private Map<Long, LinkedList<Reservation>> interviewQueueMap;
    private final CompanyRepository companyRepository;

    public InterviewReservationQueue(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        interviewQueueMap = new HashMap<>();
    }


    /**
     * 인터뷰를 진행하는 회사의 대기열 추가.
     *
     * @param company
     */
    public void addCompany(Company company) {
        if (!interviewQueueMap.containsKey(company.getId())) {
            interviewQueueMap.put(company.getId(), new LinkedList<>());
        }
    }

    /**
     * 인터뷰를 진행하는 회사의 대기열 제거.
     *
     * @param companyId
     * @return
     */
    public boolean removeQueue(Long companyId) {
        return interviewQueueMap.remove(companyId) != null;
    }


    /**
     * 회사 식별 번호를 이용해서 인터뷰 대기자를 추가
     *
     * @param companyId
     * @param student
     */
    public void joinInterviewReservation(Long companyId, Student student) {
        if (interviewQueueMap.containsKey(companyId)) {
            Queue<Reservation> reservations = interviewQueueMap.get(companyId);
            Company company = companyRepository.getCompany(companyId).get();
            int order = company.getInterviewCount();
            company.increaseInterviewCount();
            Reservation reservation = new Reservation(student, order);
            reservations.add(reservation);
        }
    }

    public void joinInterviewReservation(Long companyId, Reservation reservation) {
        if (interviewQueueMap.containsKey(companyId)) {
            Queue<Reservation> reservations = interviewQueueMap.get(companyId);
            reservations.add(reservation);
        }
    }


    /**
     * 인터뷰 시작을 위해 가장 첫번째 대기 순서를 제거
     *
     * @param companyId
     * @return
     */
    public Reservation removeFirstReservation(Long companyId) {
        return interviewQueueMap.get(companyId).remove();
    }

    /**
     * 인터뷰 대기를 제거
     *
     * @param companyNo
     * @param order
     */
    public Reservation removeReservationByOrder(Long companyNo, Long order) {
        List<Reservation> reservations = interviewQueueMap.get(companyNo);

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getOrder() == order) {
                return reservations.remove(i);
            }
        }

        return null;
    }

    /**
     * 전체 대기 목록 조회
     *
     * @return
     */
    public Map<Long, List<Reservation>> getReservations() {
        interviewQueueMap.keySet()
                .forEach(
                        companyId -> {
                            interviewQueueMap.get(companyId).sort(new ReservationComparator());
                        }
                );

        return interviewQueueMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ArrayList<>(entry.getValue())
                ));
    }

    public void setReservations(Map<Long, LinkedList<Reservation>> reservations) {
        this.interviewQueueMap = reservations;
    }

    static class ReservationComparator implements Comparator<Reservation> {

        @Override
        public int compare(Reservation o1, Reservation o2) {
            // 인터뷰를 더 많이한 학생은 적게한 학생보다 크다
            if ((o1.getStudent().getInterviewCount() - o2.getStudent().getInterviewCount()) != 0) {
                return o1.getStudent().getInterviewCount() - o2.getStudent().getInterviewCount();
            } else if ((o1.getOrder() - o2.getOrder()) != 0) {
                return o1.getOrder() - o2.getOrder();
            } else {
                return o1.getStudent().getTraineeNo().compareTo(o2.getStudent().getTraineeNo());
            }
        }
    }

}
