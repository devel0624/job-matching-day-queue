package com.nhnacademy.jobmatchingday.common;

import com.nhnacademy.jobmatchingday.domain.Student;
import com.nhnacademy.jobmatchingday.manager.InterviewBackUpManager;
import com.nhnacademy.jobmatchingday.repo.StudentRepository;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Component
public class ApplicationStarter implements ApplicationRunner {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ApplicationStarter.class);
    Logger logger = Logger.getLogger("ApplicationStarter");

    public ApplicationStarter(StudentRepository studentRepository, InterviewBackUpManager interviewBackUpManager) {
        this.studentRepository = studentRepository;
        this.interviewBackUpManager = interviewBackUpManager;
    }

    private final StudentRepository studentRepository;
    private final InterviewBackUpManager interviewBackUpManager;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            interviewBackUpManager.loadBackup();
        } catch (Exception e) {
            log.error(e.getMessage());
            traineeMap();
        }
    }

    public void traineeMap() throws IOException {
        File file = new File("trainees.csv");

        try {
            FileReader fileReader = new FileReader(file);

            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);

            for (CSVRecord record : csvParser.getRecords()){
                String traineeNo = record.get(0);
                String traineeName = record.get(1);

                Student student = new Student(traineeNo, traineeName);
                studentRepository.save(student);
            }
            System.out.println("trainees loaded successfully.");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
