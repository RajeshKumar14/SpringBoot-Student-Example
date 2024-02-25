package com.rajitblog.student.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMsg {
    @JsonProperty("status")
    private String status;

    @JsonProperty ("data")
    private Object data;

    @JsonProperty ("message")
    private String message;

    @JsonProperty ("request_type")
    private String request_type;

    @JsonProperty ("user_id")
    private String user_id;
}

