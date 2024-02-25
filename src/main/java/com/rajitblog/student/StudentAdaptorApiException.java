package com.rajitblog.student;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentAdaptorApiException extends RuntimeException{
    private String url;
    private String message;
    private String status;
    private String user_id;
    public StudentAdaptorApiException(String message, String url, String messageDesc, String status) {
        this(message, null, url, messageDesc, status,"");
    }

    public StudentAdaptorApiException(String message, Throwable cause, String url) {
        this(message, cause, url, "", "","");
    }

    public StudentAdaptorApiException(String message, Throwable cause, String url, String messageDesc, String status, String user_id) {
        super(message, cause);
        this.url = url;
        this.message = messageDesc;
        this.status = status;
        this.user_id=user_id;
    }
}
