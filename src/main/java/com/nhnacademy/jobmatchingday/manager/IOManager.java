package com.nhnacademy.jobmatchingday.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.springframework.stereotype.Component;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Component
public class IOManager {

    private final ObjectMapper objectMapper;

    public IOManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String readFile(String fileName){

        try {
           return objectMapper.readValue(new File(fileName), String.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean writeFile(String fileName, Object object){

        try {
            String s = objectMapper.writeValueAsString(object);
            File file = new File(fileName);
            file.createNewFile();

            OutputStreamWriter writer =  new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

            writer.write(s);
            writer.close();

            return true;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
