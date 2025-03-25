package com.nhnacademy.jobmatchingday.service;

import com.nhnacademy.jobmatchingday.domain.Reservation;
import com.nhnacademy.jobmatchingday.interview.queue.InterviewReservationQueue;
import com.nhnacademy.jobmatchingday.interview.stack.InterviewProcessStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.springframework.stereotype.Service;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Service
public class InterviewProcessService {

    private final InterviewProcessStack interviewProcessStack;
    private final InterviewReservationQueue interviewReservationQueue;

    public InterviewProcessService(InterviewProcessStack interviewProcessStack,
                                   InterviewReservationQueue interviewReservationQueue) {
        this.interviewProcessStack = interviewProcessStack;
        this.interviewReservationQueue = interviewReservationQueue;
    }

    public void interviewStark(Long companyId){
        Reservation reservation = interviewReservationQueue.removeFirstReservation(companyId);
        interviewProcessStack.push(companyId, reservation);
    }

    public void rollback(Long companyId){
        Reservation reservation = interviewProcessStack.pop(companyId);
        interviewReservationQueue.joinInterviewReservation(companyId, reservation);
    }

    public void finishInterview(Long companyId){
        Stack<Reservation> reservations = interviewProcessStack.getStack(companyId);

        while(!reservations.isEmpty()){
            Reservation reservation = reservations.pop();
            reservation.getStudent().incrementInterviewCount();
        }
    }

    public Map<Long, List<Reservation>> getInterviewProcessStack() {

        Map<Long, Stack<Reservation>> interviewProcess = interviewProcessStack.getInterviewProcessStack();
        Map<Long, List<Reservation>> interviewProcessList = new HashMap<>();

        interviewProcess.forEach((k, v) -> interviewProcessList.put(k, new ArrayList<>(v)));

        return interviewProcessList;
    }
}
