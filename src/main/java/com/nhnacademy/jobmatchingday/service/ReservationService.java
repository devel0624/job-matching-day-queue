package com.nhnacademy.jobmatchingday.service;

import com.nhnacademy.jobmatchingday.domain.Reservation;
import com.nhnacademy.jobmatchingday.domain.Student;
import com.nhnacademy.jobmatchingday.interview.queue.InterviewReservationQueue;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Service
public class ReservationService {

    private final InterviewReservationQueue interviewReservationQueue;

    public ReservationService(InterviewReservationQueue interviewReservationQueue) {
        this.interviewReservationQueue = interviewReservationQueue;
    }

    public boolean registerReservation(Long companyId, Student student) {
        interviewReservationQueue.joinInterviewReservation(companyId, student);
        return true;
    }

    public void deleteReservation(Long companyNo, Long order) {
        interviewReservationQueue.removeReservationByOrder(companyNo, order);
    }

    public Map<Long, List<Reservation>> getReservations() {
        return interviewReservationQueue.getReservations();
    }
}
