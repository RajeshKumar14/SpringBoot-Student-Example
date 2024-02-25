package com.rajitblog.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppException extends RuntimeException {
    @JsonIgnore
    private HttpStatus statusCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty ("user_id")
    private String user_id;

    private String message;

    private String header;

    private Map<String, Object> information;

    @Builder
    public AppException(final String message, final HttpStatus statusCode, final String status, final String header,
                        final Map<String, Object> information, final String user_id) {
        super(message);
        this.statusCode = statusCode;
        this.user_id = user_id;
        this.status = status;
        this.message = message;
        this.header = header;
        this.information = information;
    }

    public Map<String, Object> getParamsMap() {
        final Map<String, Object> params = new HashMap<>();
        params.put("status", this.status);
        params.put("message", this.message);
        params.put("user_id", this.user_id);
        params.put("header", this.header);
        params.put("information", this.information);
        return params;
    }
}
