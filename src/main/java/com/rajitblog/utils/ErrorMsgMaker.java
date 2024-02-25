package com.rajitblog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajitblog.student.dto.ErrorMsg;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class ErrorMsgMaker {

    public Optional<ErrorMsg> makeFrom(String errorObject) {
        try {
            return Optional.of(new ObjectMapper().readValue(errorObject, ErrorMsg.class));
        } catch (IOException e) {
            log.info("error in mapping");
            return Optional.empty();
        }
    }
}