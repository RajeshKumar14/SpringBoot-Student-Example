package com.rajitblog;

import com.rajitblog.error.BusinessError;
import com.rajitblog.student.StudentAdaptorApiException;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private BusinessError statusCode;
    private Map<String, Object> information;

    public BusinessException(String message, BusinessError statusCode, Map<String, Object> information) {
        this(message, null, statusCode, information);
    }

    public BusinessException(String message, BusinessError statusCode) {
        this(message, null, statusCode, Collections.emptyMap());
    }

    public BusinessException(String message, Throwable cause, BusinessError statusCode) {
        this(message, cause, statusCode, Collections.emptyMap());
    }

    public BusinessException(String message, Throwable cause, BusinessError statusCode, Map<String, Object> information) {
        super(message, cause);
        this.statusCode = statusCode;
        this.information = information;
    }

    public BusinessException(StudentAdaptorApiException digilockerApiException, BusinessError statusCode) {
        super(digilockerApiException.getMessage(), digilockerApiException.getCause());
        HashMap<String, Object> information = new HashMap<>();
        information.put("status", digilockerApiException.getStatus());
        information.put("message", digilockerApiException.getMessage());
        information.put("user_id", digilockerApiException.getUser_id());
        this.information = information;
        this.statusCode = statusCode;
    }
}
