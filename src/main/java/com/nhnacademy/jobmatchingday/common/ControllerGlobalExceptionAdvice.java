package com.nhnacademy.jobmatchingday.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : 이성준
 * @since : 1.0
 */

@ControllerAdvice
public class ControllerGlobalExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ControllerGlobalExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {

        log.error(ex.getMessage(), ex);

        return "redirect:/";
    }
}
